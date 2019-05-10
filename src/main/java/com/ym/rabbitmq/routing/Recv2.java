package com.ym.rabbitmq.routing;

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
    private static final String QUEUE_NAME = "test_exchange_direct_2";
    private static  final String EXCHANGER_NAME = "test_routing";


    public static void main(String[] args) throws Exception{
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //从连接中获取通道
        final Channel channel = connecction.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机,转发器
        channel.queueBind(QUEUE_NAME,EXCHANGER_NAME,"error");
        channel.queueBind(QUEUE_NAME,EXCHANGER_NAME,"info");
        channel.queueBind(QUEUE_NAME,EXCHANGER_NAME,"warning");
        //保证每次只能发1个
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
                    System.out.println("[2] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }

        };
        //消息应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
