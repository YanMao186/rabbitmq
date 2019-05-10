package com.ym.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ym.rabbitmq.util.ConnectionUtils;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-10 17:17
 **/

public class Send {
    private static final String EXCHANGE_NAME="test_topic";
    public static void main(String[] args) throws Exception{
        Connection connecction = ConnectionUtils.getConnecction();
        Channel channel = connecction.createChannel();
        //设置为主题topic模式
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String msg = "商品.........";
        //设置商品类型为删除
        channel.basicPublish(EXCHANGE_NAME,"goods.del",null,msg.getBytes());
        System.out.println("----send---"+msg);
        channel.close();
        connecction.close();
    }
}
