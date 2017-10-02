package com.lee.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {
    private static final Logger logger = LoggerFactory.getLogger(WsController.class);

    @MessageMapping("/hello/{topic}")
    @SendTo("/queue/hello")
//    @SendToUser("/queue/hello")
    public String send(@DestinationVariable("topic") String topic, String str) throws Exception {
        logger.info("topic:{}", topic);
        if (StringUtils.isNotBlank(str)) {
            return "echo back "+ str;
        } else {
            return "你什么也没传";
        }
    }
}
