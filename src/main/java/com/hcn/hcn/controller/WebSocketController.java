package com.hcn.hcn.controller;

import com.hcn.hcn.model.Message;
import com.hcn.hcn.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat/{headerId}")
    @SendTo("/topic/chat/{headerId}")
    public Message sendMessage(Message message) {
        //웹소켓을 이용해 메시지 디비에 저장
        System.out.println("새로 받은 WebSocket message: " + message);
        messageService.saveMessage(message);

        return message;
    }
}
