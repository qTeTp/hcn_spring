package com.hcn.hcn.controller;

import com.hcn.hcn.model.ChatHeader;
import com.hcn.hcn.service.ChatHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chats")
public class ChatHeaderController {

    @Autowired
    private ChatHeaderService chatHeaderService;

    @GetMapping
    public List<ChatHeader> getChats(@RequestParam("userId") String userId) {
        return chatHeaderService.getChatHeadersByUserId(userId);
    }

    @PostMapping("/sendPoints")
    public ResponseEntity<Void> sendPoints(
            @RequestParam("headerId") Long headerId,
            @RequestParam("fromId") String fromId,
            @RequestParam("toId") String toId,
            @RequestParam("amount") Float amount) {
        chatHeaderService.sendPoints(headerId, fromId, toId, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{headerId}/sentPoints")
    public ResponseEntity<Map<String, Float>> getSentPoints(@PathVariable Long headerId) {
        Float sentPoints = chatHeaderService.getSentPoints(headerId);
        return ResponseEntity.ok(Map.of("sent_points", sentPoints));
    }
}
