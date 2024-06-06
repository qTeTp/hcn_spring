package com.hcn.hcn.controller;

import com.hcn.hcn.model.ChatHeader;
import com.hcn.hcn.service.ChatHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatHeaderController {

    @Autowired
    private ChatHeaderService chatHeaderService;

    @GetMapping
    public List<ChatHeader> getChats(@RequestParam("userId") String userId) {
        return chatHeaderService.getChatHeadersByUserId(userId);
    }
}
