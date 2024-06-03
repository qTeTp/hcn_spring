package com.hcn.hcn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private int postId;
    private String content;
    private List<Comment> comments;
}
