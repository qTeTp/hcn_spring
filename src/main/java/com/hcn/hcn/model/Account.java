package com.hcn.hcn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private String username;
    private long followers;

    @JsonProperty("profile_views")
    private long profileViews;

    @JsonProperty("all_likes")
    private long allLikes;

    private int commentCount;
    private int replyCount;
    private Map<String, Integer> followers_age_range = new HashMap<>();
    private List<Double> gender_ratio;
    private List<Post> posts;

    public void calculateCommentAndReplyCounts() {
        int totalComments = 0;
        int totalReplies = 0;

        if (posts != null) {
            for (Post post : posts) {
                if (post.getComments() != null) {
                    totalComments += post.getComments().size();
                    for (Comment comment : post.getComments()) {
                        if (comment.getReplies() != null) {
                            totalReplies += comment.getReplies().size();
                        }
                    }
                }
            }
        }

        this.commentCount = totalComments;
        this.replyCount = totalReplies;
    }

    public double getMalePercentage() {
        return this.gender_ratio != null && this.gender_ratio.size() == 2 ? this.gender_ratio.get(0) : 0.0;
    }

    public double getFemalePercentage() {
        return this.gender_ratio != null && this.gender_ratio.size() == 2 ? this.gender_ratio.get(1) : 0.0;
    }
}
