package com.ym.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.ym.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-09 11:12
 **/

public class Recv {
    private static final String QUEUE_NAME = "test_queue";
//    private static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws Exception {
//   Recv.oldApi();
        Recv.newApi();
    }
    public static void oldApi()throws Exception{
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //创建频道
        Channel channel = connecction.createChannel();

        QueueingConsumer Consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,true,Consumer);
        while (true){
            QueueingConsumer.Delivery delivery = Consumer.nextDelivery();
            String s = new String(delivery.getBody());
            System.out.println(s);
        }
    }
    public static void newApi() throws Exception {
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //创建频段
        Channel channel = connecction.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("new api recv:"+msg);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
