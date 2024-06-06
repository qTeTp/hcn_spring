package com.hcn.hcn.service;

import com.hcn.hcn.model.ChatHeader;
import com.hcn.hcn.repository.ChatHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatHeaderService {

    @Autowired
    private ChatHeaderRepository chatHeaderRepository;

    public ChatHeader saveChatHeader(ChatHeader chatHeader) {
        return chatHeaderRepository.save(chatHeader);
    }

    public List<ChatHeader> getChatHeadersByUserId(String userId) {
        return chatHeaderRepository.findByUserId(userId);
    }

    public void updateChatHeader(Long headerId, String lastId, String lastMessage, String time) {
        ChatHeader chatHeader = chatHeaderRepository.findById(headerId)
                .orElseThrow(() -> new RuntimeException("Header not found"));
        chatHeader.setLastId(lastId);
        chatHeader.setLastMessage(lastMessage);
        chatHeader.setTime(time);
        chatHeaderRepository.save(chatHeader);
    }
}
