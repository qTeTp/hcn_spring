package com.hcn.hcn.controller;

import com.hcn.hcn.model.Message;
import com.hcn.hcn.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    @GetMapping("/byHeaderId/{headerId}")
    public List<Message> getMessagesByHeaderId(@PathVariable("headerId") Long headerId) {
        return messageService.getMessagesByHeaderId(headerId);
    }
}
