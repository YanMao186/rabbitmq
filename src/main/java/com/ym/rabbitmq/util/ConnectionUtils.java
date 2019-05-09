package com.ym.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: SpringMVCDemo
 * @description:
 * @author: Mr.Yan
 * @create: 2019-05-09 10:42
 **/

public class ConnectionUtils {

    /**
     * 获取链接的工具类
     */
    public static Connection getConnecction() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置链接地址
        factory.setHost("localhost");
        //设置端口号
        factory.setPort(5672);
        //设置vhost
        factory.setVirtualHost("/");
        //设置用户名
        factory.setUsername("guest");
        //设置密码
        factory.setPassword("guest");
        return factory.newConnection();
    }
}
