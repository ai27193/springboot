package com.ou.springbootmybatis.rabbitlistener;

import com.ou.springbootmybatis.service.impl.UserServiceImpl;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Component


public class DirectReceiver {
    @Autowired
    UserServiceImpl userServiceImpl;

    //可以返回消息 消费者需要使用convertSendAndReceive
//    @RabbitHandler
//    @SendTo("TestDirectQueue")
//    public String process(Map testMessage) {
//        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
//        User user=userServiceImpl.selectByName(testMessage.get("name").toString());
//        return user.toString();
//    }
    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    @RabbitHandler
    public void process(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //手工ack
        try {
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("确认消费消息: " + new String(message.getBody()));
    }
}