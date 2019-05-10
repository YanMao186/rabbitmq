package com.ym.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ym.rabbitmq.util.ConnectionUtils;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-10 16:41
 **/

public class Send {
    private static  final String EXCHANGER_NAME = "test_routing";
    public static void main(String[] args)throws Exception {
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //获取通道
        Channel channel = connecction.createChannel();
        //声明交换机,类型为direct
        channel.exchangeDeclare(EXCHANGER_NAME,"direct");
        //发送消息
        String msg = "hello routing";
        //routingKey取值范围,error,info,warning
        String routingKey = "info";
        channel.basicPublish(EXCHANGER_NAME,routingKey,null,msg.getBytes());
        channel.close();
        connecction.close();
    }
}
