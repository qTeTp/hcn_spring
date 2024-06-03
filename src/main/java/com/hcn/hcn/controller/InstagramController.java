package com.hcn.hcn.controller;

import com.hcn.hcn.service.JsonAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstagramController {

    @Autowired
    private JsonAnalysisService jsonAnalysisService;

    @GetMapping("/analyze")
    public String analyze(@RequestParam String filePath) {
        jsonAnalysisService.analyze(filePath);
        return "Analysis completed for file: " + filePath;
    }
}
