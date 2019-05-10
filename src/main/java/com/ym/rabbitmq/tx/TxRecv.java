package com.ym.rabbitmq.tx;

import com.rabbitmq.client.*;
import com.ym.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-09 17:30
 **/

public class TxRecv {
    public static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws Exception {
//        //获取连接
//        Connection connecction = ConnectionUtils.getConnecction();
//        //从连接中获取通道
//        Channel channel = connecction.createChannel();
//
//        //声明队列
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println(new String(body, "utf-8"));
//            }
//        });
//    }
        Connection connecction = ConnectionUtils.getConnecction();
        Channel channel = connecction.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
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
                }
            }

        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
