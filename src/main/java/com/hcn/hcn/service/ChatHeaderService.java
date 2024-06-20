package com.hcn.hcn.service;

import com.hcn.hcn.model.ChatHeader;
import com.hcn.hcn.repository.ChatHeaderRepository;
import com.hcn.hcn.model.WebUserData;
import com.hcn.hcn.repository.WebUserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatHeaderService {

    @Autowired
    private ChatHeaderRepository chatHeaderRepository;

    @Autowired
    private WebUserDataRepository webUserDataRepository;

    public ChatHeader saveChatHeader(ChatHeader chatHeader) {
        return chatHeaderRepository.save(chatHeader);
    }

    public List<ChatHeader> getChatHeadersByUserId(String userId) {
        return chatHeaderRepository.findByUserId(userId);
    }

    public void updateChatHeader(Long headerId, String lastId, String lastMessage, String time) {
        ChatHeader chatHeader = chatHeaderRepository.findById(headerId)
                .orElseThrow(() -> new RuntimeException("Header not found"));
        chatHeader.setLast_id(lastId);
        chatHeader.setLast_message(lastMessage);
        chatHeader.setTime(time);
        chatHeaderRepository.save(chatHeader);
    }

    public void updateAllPoints(Long headerId, Float pay) {
        Optional<ChatHeader> optionalHeader = chatHeaderRepository.findById(headerId);
        if (optionalHeader.isPresent()) {
            ChatHeader chatHeader = optionalHeader.get();
            chatHeader.setAll_points(chatHeader.getAll_points() + pay);
            chatHeaderRepository.save(chatHeader);
        }
    }

    public void sendPoints(Long headerId, String fromId, String toId, Float amount) {
        Optional<ChatHeader> optionalHeader = chatHeaderRepository.findById(headerId);
        if (optionalHeader.isPresent()) {
            ChatHeader chatHeader = optionalHeader.get();
            WebUserData fromUser = webUserDataRepository.findByUserId(fromId);
            WebUserData toUser = webUserDataRepository.findByUserId(toId);

            if (fromUser != null && toUser != null) {
                if (fromUser.getPoints() >= amount) {
                    fromUser.setPoints(fromUser.getPoints() - amount);
                    toUser.setPoints(toUser.getPoints() + amount);
                    chatHeader.setSended_points((chatHeader.getSended_points() != null ? chatHeader.getSended_points() : 0) + amount);
                    webUserDataRepository.save(fromUser);
                    webUserDataRepository.save(toUser);
                    chatHeaderRepository.save(chatHeader);
                } else {
                    throw new RuntimeException("Insufficient points");
                }
            } else {
                throw new RuntimeException("User not found");
            }
        } else {
            throw new RuntimeException("Header not found");
        }
    }

    public Float getSentPoints(Long headerId) {
        Optional<ChatHeader> optionalHeader = chatHeaderRepository.findById(headerId);
        if (optionalHeader.isPresent()) {
            ChatHeader chatHeader = optionalHeader.get();
            return chatHeader.getSended_points();
        } else {
            throw new RuntimeException("Header not found");
        }
    }

    public void updateHeaderForPhotoMessage(Long headerId, String lastMessage, String time) {
        ChatHeader chatHeader = chatHeaderRepository.findById(headerId).orElseThrow();
        chatHeader.setLast_message(lastMessage);
        chatHeader.setTime(time);
        chatHeaderRepository.save(chatHeader);
    }
}
