package com.lee.controller;

import com.lee.entity.GirlFriend;
import com.lee.ws.OutMsg;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    @MessageMapping("/chat/{topic}")
    @SendTo("/topic/messages")
    public OutMsg send(@DestinationVariable("topic") String topic, GirlFriend girlFriend) throws Exception {
        return new OutMsg(girlFriend.getName(), topic);
    }
}
