package com.ym.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ym.rabbitmq.util.ConnectionUtils;

/**
 * @program: SpringMVCDemo
 * @description:普通模式
 * @author: Mr.Yan
 * @create: 2019-05-10 21:39
 **/

public class Send {
    private static final String QUEUE_NAME="test_queue_confirm1";

    public static void main(String[] args) throws Exception{
        Connection connecction = ConnectionUtils.getConnecction();
        Channel channel = connecction.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //生产者调用confirmSelect将channel设置为confirm模式
        channel.confirmSelect();
        String msg = "hello confirm message";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        //普通模式发一条数据
        if (!channel.waitForConfirms()) {
            System.out.println("message send failed");
        }else {
            System.out.println("message send ok");
        }
        channel.close();
        connecction.close();
    }
}
