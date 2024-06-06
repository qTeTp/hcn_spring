package com.hcn.hcn.controller;

import com.hcn.hcn.model.Proposal;
import com.hcn.hcn.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping
    public ResponseEntity<Proposal> createProposal(
            @RequestParam("userId") String userId,
            @RequestParam("photoUrl") MultipartFile photoUrl,
            @RequestParam("goodName") String goodName,
            @RequestParam("goodDetail") String goodDetail,
            @RequestParam("goodRequire") String goodRequire,
            @RequestParam("term") String term,
            @RequestParam("payWay") String payWay,
            @RequestParam("pay") Float pay) {

        String fileName = System.currentTimeMillis() + "_" + photoUrl.getOriginalFilename();
        Path filePath = Paths.get(uploadPath, fileName);

        try {
            Files.write(filePath, photoUrl.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        Proposal proposal = new Proposal();
        proposal.setUserId(userId);
        proposal.setPhotoUrl(fileName); // 파일명만 저장
        proposal.setGoodName(goodName);
        proposal.setGoodDetail(goodDetail);
        proposal.setGoodRequire(goodRequire);
        proposal.setTerm(term);
        proposal.setPayWay(payWay);
        proposal.setPay(pay);

        Proposal savedProposal = proposalService.saveProposal(proposal);
        return ResponseEntity.ok(savedProposal);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<List<Proposal>> getProposalsByUserId(@PathVariable("userId") String userId) {
        List<Proposal> proposals = proposalService.getProposalsByUserId(userId);
        return ResponseEntity.ok(proposals);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not find or read the file: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching the file: " + filename, e);
        }
    }
}
