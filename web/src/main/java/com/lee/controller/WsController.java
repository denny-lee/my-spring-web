package com.lee.controller;

import com.lee.OrderStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WsController {

    @MessageMapping("/hello/{topic}")
    @SendToUser("/queue/hello")
    public OrderStatus send(@DestinationVariable("topic") String topic, OrderStatus orderStatus, Principal principal) throws Exception {
        System.out.println("===========topic:"+topic);
        System.out.println("===========userName:"+ principal.getName());
        return new OrderStatus(orderStatus.getOrderNo(), topic);
    }
}
