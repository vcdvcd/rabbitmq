package com.ww.rabbitmq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.ww.rabbitmq.util.RabbitmqUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class EmitLogDirect {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        Map<String,String> map =new HashMap<>();
        map.put("info","yebuxingba");
        map.put("warning","weigejinggaonile");
        map.put("error","不识好歹是吧！🤬");
//        Scanner sc =new Scanner(System.in);
//        while(sc.hasNext()){
//            String next = sc.next();
//            channel.basicPublish(EXCHANGE_NAME,"info",null,next.getBytes(StandardCharsets.UTF_8));
//            System.out.println("ok");
//        }
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for(Map.Entry<String,String > e: entries){
            channel.basicPublish(EXCHANGE_NAME,e.getKey(),null,e.getValue().getBytes());
            System.out.println("ok");
        }
    }
}
