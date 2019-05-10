package com.ym.rabbitmq.publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ym.rabbitmq.util.ConnectionUtils;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-09 17:08
 **/

public class Send {
    private static final String EXCHANGER_NAME = "exchange_fanout";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //获取通道
        Channel channel = connecction.createChannel();
//      //声明交换机,fanout指定分发策略
        channel.exchangeDeclare(EXCHANGER_NAME,"fanout");
        //发送消息
        for (int i = 0; i < 50; i++) {
            String msg = "Hello "+ i;
            channel.basicPublish(EXCHANGER_NAME,"",null,msg.getBytes());
            Thread.sleep(100);
            System.out.println(msg);

        }
        channel.close();
        connecction.close();
    }
}
