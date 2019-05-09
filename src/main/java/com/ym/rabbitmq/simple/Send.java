package com.ym.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ym.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-09 11:00
 **/

public class Send {
    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //从连接中获取一个通道
        Channel channel = connecction.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "hello simple";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("消息已发送");
        channel.close();
        connecction.close();
    }
}
