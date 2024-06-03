package com.hcn.hcn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private int commentId;
    private String text;
    private List<Reply> replies;
}
