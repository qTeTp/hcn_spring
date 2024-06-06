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

    @Autowired
    private ChatHeaderService chatHeaderService;

    public Message saveMessage(Message message) {
        Message savedMessage = messageRepository.save(message);

        //cahtheader last 내용 업데이트
        chatHeaderService.updateChatHeader(
                message.getHeaderId(),
                message.getFromId(),
                message.getContent(),
                message.getTime()
        );

        return savedMessage;
    }

    public List<Message> getMessagesByHeaderId(Long headerId) {
        return messageRepository.findByHeaderId(headerId);
    }
}
