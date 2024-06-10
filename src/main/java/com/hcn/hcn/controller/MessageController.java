package com.hcn.hcn.controller;

import com.hcn.hcn.model.Message;
import com.hcn.hcn.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @PostMapping
    public ResponseEntity<Message> saveMessage(
            @RequestParam("headerId") Long headerId,
            @RequestParam("fromId") String fromId,
            @RequestParam("toId") String toId,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam("messageType") Boolean messageType) {

        Message message = new Message();
        message.setHeaderId(headerId);
        message.setFromId(fromId);
        message.setToId(toId);
        message.setMessageType(messageType);
        message.setTime(LocalDateTime.now().format(formatter));

        if (Boolean.TRUE.equals(messageType) && photo != null) { // 사진 메시지인 경우
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get(uploadPath + fileName);

            try {
                Files.write(filePath, photo.getBytes());
                message.setPhotoUrl(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }
        } else { // 일반 메시지인 경우
            message.setContent(content);
        }

        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/byHeaderId/{headerId}")
    public List<Message> getMessagesByHeaderId(@PathVariable("headerId") Long headerId) {
        return messageService.getMessagesByHeaderId(headerId);
    }
}
