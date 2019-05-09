package com.ym.rabbitmq.work;

import com.rabbitmq.client.*;
import com.ym.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-09 17:30
 **/

public class Recv2 {
        private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception{
        Connection connecction = ConnectionUtils.getConnecction();
        Channel channel = connecction.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String msg = new String(body,"utf-8");
                System.out.println("recv2:"+msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                }
            }

        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
