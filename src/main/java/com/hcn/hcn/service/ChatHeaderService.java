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

    public List<ChatHeader> getChatHeadersByUserId(String userId) {
        return chatHeaderRepository.findByUserId(userId);
    }
}
