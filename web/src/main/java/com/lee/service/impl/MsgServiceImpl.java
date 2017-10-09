package com.lee.service.impl;

import com.lee.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Description:
 * On 2017/9/11 9:34 created by LW
 */
@Service
public class MsgServiceImpl implements MsgService {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public MsgServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void send(String msg) {
//        messagingTemplate.convertAndSend("/user/{userId}/queue/hello", msg);
        messagingTemplate.convertAndSendToUser("fabrice","/queue/hello", msg);
    }
}
