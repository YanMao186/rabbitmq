package com.ym.rabbitmq.work;

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
    public static final String QUEUE_NAME= "test_work_queue";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //获取通道
        Channel channel = connecction.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送消息
        for (int i = 0; i < 50; i++) {
            String msg = "Hello"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(1000);

        }
        channel.close();
        connecction.close();
    }
}
