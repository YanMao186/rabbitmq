package com.ym.rabbitmq.workfair;

import com.rabbitmq.client.*;
import com.ym.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-09 17:30
 **/

public class Recv1 {
        private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception{
        Connection connecction = ConnectionUtils.getConnecction();
        final Channel channel = connecction.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String msg = new String(body,"utf-8");
                System.out.println("recv1:"+msg);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }

        };
        //消息应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
