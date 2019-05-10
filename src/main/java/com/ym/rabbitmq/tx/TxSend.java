package com.ym.rabbitmq.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ym.rabbitmq.util.ConnectionUtils;

/**
 * @program: SpringMVCDemo
 * @description:rabbitmq事务管理
 * @author: Mr.Yan
 * @create: 2019-05-10 19:27
 **/

public class TxSend {
    public static final String QUEUE_NAME= "test_queue_tx";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connecction = ConnectionUtils.getConnecction();
        //获取通道
        Channel channel = connecction.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         * 每个消费者发送确认消息之前,消息队列不发送下一个消息到消费者,一次只处理一个消息
         * 限制发送给同一个消费者不得超过一条消息
         */
        channel.basicQos(1);
        //发送消息

            String msg = "Hello tx message";
            try {
                //开启事务
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            //提交事务
//                int i = 2 / 0;
            channel.txCommit();
            Thread.sleep(100);
            System.out.println(msg);
            }catch (Exception e) {
                //回滚事务
                channel.txRollback();
                System.out.println("send message rollback");
            }
        channel.close();
        connecction.close();
    }
}
