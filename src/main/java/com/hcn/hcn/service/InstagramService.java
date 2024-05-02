package com.hcn.hcn.service;

import com.hcn.hcn.model.InstagramData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class InstagramService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InstagramData loadInstagramData() throws IOException {
        Path path = new FileSystemResource("C:/Users/k3553/Documents/exexpo/insta.json").getFile().toPath();
        String jsonContent = Files.readString(path);
        return objectMapper.readValue(jsonContent, InstagramData.class);
    }
}
