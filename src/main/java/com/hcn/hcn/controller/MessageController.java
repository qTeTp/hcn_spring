package com.hcn.hcn.controller;

import com.hcn.hcn.model.Message;
import com.hcn.hcn.service.MessageService;
import com.hcn.hcn.service.ChatHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ChatHeaderService chatHeaderService;

    @Value("${upload.path}")
    private String uploadPath;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    // 메시지 보내기 - JSON 형식
    @PostMapping("/json")
    public ResponseEntity<Message> saveMessageJson(@RequestBody Message message) {
        message.setTime(LocalDateTime.now().format(formatter));

        String lastMessage = "";
        if (Boolean.TRUE.equals(message.getMessageType()) && message.getPhotoUrl() != null) { // 사진 메시지인 경우
            lastMessage = "사진을 보냈습니다";
        } else { // 일반 메시지인 경우
            lastMessage = message.getContent() != null ? message.getContent() : "";
        }

        Message savedMessage = messageService.saveMessage(message);

        // 헤더 업데이트
        chatHeaderService.updateChatHeader(message.getHeaderId(), message.getFromId(), lastMessage, LocalDateTime.now().format(formatter));

        return ResponseEntity.ok(savedMessage);
    }

    // 메시지 보내기 - 파일 업로드 형식
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

        String lastMessage = "";
        if (Boolean.TRUE.equals(messageType) && photo != null) { // 사진 메시지인 경우
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get(uploadPath + fileName);

            try {
                Files.write(filePath, photo.getBytes());
                message.setPhotoUrl(fileName); // 파일 이름만 저장
                message.setContent("사진을 보냈습니다"); // content 필드에 "사진을 보냈습니다" 저장
                lastMessage = "사진을 보냈습니다";
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }
        } else { // 일반 메시지인 경우
            message.setContent(content != null ? content : ""); // content가 null일 경우 빈 문자열로 설정
            lastMessage = content != null ? content : "";
        }

        Message savedMessage = messageService.saveMessage(message);

        // 헤더 업데이트
        chatHeaderService.updateChatHeader(headerId, fromId, lastMessage, LocalDateTime.now().format(formatter));

        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/byHeaderId/{headerId}")
    public List<Message> getMessagesByHeaderId(@PathVariable("headerId") Long headerId) {
        return messageService.getMessagesByHeaderId(headerId);
    }
}

