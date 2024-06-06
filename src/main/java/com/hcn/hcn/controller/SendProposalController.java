package com.hcn.hcn.controller;

import com.hcn.hcn.model.ChatHeader;
import com.hcn.hcn.model.SendProposal;
import com.hcn.hcn.service.ChatHeaderService;
import com.hcn.hcn.service.SendProposalService;
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
@RequestMapping("/api/sendProposal")
public class SendProposalController {

    @Autowired
    private SendProposalService sendProposalService;

    @Autowired
    private ChatHeaderService headerService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping
    public ResponseEntity<SendProposal> createSendProposal(
            @RequestParam("fromId") String fromId,
            @RequestParam("toId") String toId,
            @RequestParam("photoUrl") MultipartFile photoUrl,
            @RequestParam("goodName") String goodName,
            @RequestParam("goodDetail") String goodDetail,
            @RequestParam("goodRequire") String goodRequire,
            @RequestParam("term") String term,
            @RequestParam("payWay") String payWay,
            @RequestParam("pay") Float pay) {

        // 새로운 Header 생성
        ChatHeader header = new ChatHeader();
        header.setFromId(fromId);
        header.setToId(toId);
        ChatHeader savedHeader = headerService.saveChatHeader(header);

        String fileName = System.currentTimeMillis() + "_" + photoUrl.getOriginalFilename();
        Path filePath = Paths.get(uploadPath + fileName);

        try {
            Files.write(filePath, photoUrl.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        SendProposal sendProposal = new SendProposal();
        sendProposal.setHeaderId(savedHeader.getId());
        sendProposal.setFromId(fromId);
        sendProposal.setToId(toId);
        sendProposal.setPhotoUrl(filePath.toString());
        sendProposal.setGoodName(goodName);
        sendProposal.setGoodDetail(goodDetail);
        sendProposal.setGoodRequire(goodRequire);
        sendProposal.setTerm(term);
        sendProposal.setPayWay(payWay);
        sendProposal.setPay(pay);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sendProposal.setTime(LocalDateTime.now().format(formatter));

        SendProposal savedSendProposal = sendProposalService.saveSendProposal(sendProposal);
        return ResponseEntity.ok(savedSendProposal);
    }

    //제안서 수정
    @PutMapping("/{id}")
    public ResponseEntity<SendProposal> updateSendProposal(
            @PathVariable("id") Long id,
            @RequestParam("fromId") String fromId,
            @RequestParam("toId") String toId,
            @RequestParam(value = "photoUrl", required = false) MultipartFile photoUrl,
            @RequestParam("goodName") String goodName,
            @RequestParam("goodDetail") String goodDetail,
            @RequestParam("goodRequire") String goodRequire,
            @RequestParam("term") String term,
            @RequestParam("payWay") String payWay,
            @RequestParam("pay") Float pay) {

        SendProposal sendProposal = sendProposalService.getSendProposalById(id);
        if (sendProposal == null) {
            return ResponseEntity.notFound().build();
        }

        if (photoUrl != null) {
            String fileName = System.currentTimeMillis() + "_" + photoUrl.getOriginalFilename();
            Path filePath = Paths.get(uploadPath + fileName);

            try {
                Files.write(filePath, photoUrl.getBytes());
                sendProposal.setPhotoUrl(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }

        sendProposal.setFromId(fromId);
        sendProposal.setToId(toId);
        sendProposal.setGoodName(goodName);
        sendProposal.setGoodDetail(goodDetail);
        sendProposal.setGoodRequire(goodRequire);
        sendProposal.setTerm(term);
        sendProposal.setPayWay(payWay);
        sendProposal.setPay(pay);

        SendProposal updatedSendProposal = sendProposalService.saveSendProposal(sendProposal);
        return ResponseEntity.ok(updatedSendProposal);
    }

    @GetMapping("/byHeaderId/{headerId}")
    public ResponseEntity<List<SendProposal>> getProposalsByHeaderId(@PathVariable("headerId") Long headerId) {
        List<SendProposal> proposals = sendProposalService.getProposalsByHeaderId(headerId);
        return ResponseEntity.ok(proposals);
    }
}
