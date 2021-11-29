package com.ww.rabbitmq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

public class EmitLogTopic {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("quick.orange.rabbit", "quick.orange.rabbit");
        map.put("lazy.orange.elephant", "lazy.orange.elephant");
        map.put("quick.orange.fox", "quick.orange.fox");
        map.put("lazy.brown.fox", "lazy.brown.fox");
        map.put("lazy.pink.rabbit", "lazy.pink.rabbit");
        map.put("quick.brown.fox", "quick.brown.fox");
        map.put("quick.orange.male.rabbit", "quick.orange.male.rabbit");
        map.put("lazy.orange.male.rabbit", "lazy.orange.male.rabbit");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for(Map.Entry<String,String> e : entries){
            channel.basicPublish(EXCHANGE_NAME,e.getKey(),null,e.getValue().getBytes());
        }
    }
}
