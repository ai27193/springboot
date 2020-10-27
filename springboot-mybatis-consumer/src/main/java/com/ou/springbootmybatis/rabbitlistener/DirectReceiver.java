package com.ou.springbootmybatis.rabbitlistener;

import com.ou.springbootmybatis.entity.User;
import com.ou.springbootmybatis.service.impl.UserServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue

public class DirectReceiver {
    @Autowired
    UserServiceImpl userServiceImpl;

    @RabbitHandler
    @SendTo("TestDirectQueue")
    public String process(Map testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
        User user=userServiceImpl.selectByName(testMessage.get("name").toString());
        return user.toString();
    }

}