package com.ww.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

public class ConfirmMessage {
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
//        publishMessageSingle();
//        publishMessageBatch();
        publishMessageAsync();
    }

    public static void publishMessageSingle() throws Exception {
        String queueName = UUID.randomUUID().toString();
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            channel.waitForConfirms();
//            if (flag)
//                System.out.println("ok");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin + "ms");
    }

    public static void publishMessageBatch() throws Exception {
        String queueName = UUID.randomUUID().toString();
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 1; i <= MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            if (i % 100 == 0)
                channel.waitForConfirms();
//            if (flag)
//                System.out.println("ok");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin + "ms");
    }

    public static void publishMessageAsync() throws Exception {
        String queueName = UUID.randomUUID().toString();
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();
        ConcurrentSkipListMap<Long,String> concurrentSkipListMap =new ConcurrentSkipListMap<>();
        ConfirmCallback ackCallback=(deliveryTag, multiple) ->{
            if (multiple){
                ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap = concurrentSkipListMap.headMap(deliveryTag + 1);
                longStringConcurrentNavigableMap.clear();//删除已经确认的消息，clear()方法删除的是原本的map里面的元素。
            }else{
                concurrentSkipListMap.remove(deliveryTag);
            }

        };
        ConfirmCallback nackCallback=(deliveryTag, multiple) ->{
            String s = concurrentSkipListMap.get(deliveryTag);
            System.out.println("未处理的消息"+s+"tag为"+deliveryTag);
        };
        channel.addConfirmListener(ackCallback,nackCallback);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(),message);
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin + "ms");
    }
}
