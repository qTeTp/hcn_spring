package com.hcn.hcn.service;

import com.hcn.hcn.model.Message;
import com.hcn.hcn.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByHeaderId(Long headerId) {
        return messageRepository.findByHeaderId(headerId);
    }
}
