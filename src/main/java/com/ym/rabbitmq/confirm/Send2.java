package com.ym.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ym.rabbitmq.util.ConnectionUtils;

/**
 * @program: SpringMVCDemo
 * @description:批量模式
 * @author: Mr.Yan
 * @create: 2019-05-10 21:39
 **/

public class Send2 {
    private static final String QUEUE_NAME="test_queue_confirm1";

    public static void main(String[] args) throws Exception{
        Connection connecction = ConnectionUtils.getConnecction();
        Channel channel = connecction.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //生产者调用confirmSelect将channel设置为confirm模式
        channel.confirmSelect();
        String msg = "hello confirm message";
        //批量发送
        for (int i = 0; i < 10; i++) {
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }
        //确认
        if (!channel.waitForConfirms()) {
            System.out.println("message send failed");
        }else {
            System.out.println("message send ok");
        }
        channel.close();
        connecction.close();
    }
}
