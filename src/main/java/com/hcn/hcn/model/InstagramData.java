package com.hcn.hcn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class InstagramData {
    private List<Account> accounts;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Account {
        private int account_id;
        private String username;
        private String profile_image;
        private String profile_comment;
        private List<String> category; // 계정 카테고리 리스트 추가
        private long followers;          // 팔로워 수
        private long followings;         // 팔로잉 수
        private long all_likes;          // 받은 좋아요 총 수
        private long profile_views;      // 프로필 조회 수
        private List<Post> posts;
    }

    @Data
    public static class Post {
        private int post_id;
        private int account_id;
        private List<String> image_urls;
        private String content;
        private String post_time;
        private List<String> hashtags;
        private long likes;
        private List<Comment> comments;
    }

    @Data
    public static class Comment {
        private int comment_id;
        private int author_id;          // 댓글 작성자 ID
        private String text;
        private String comment_date;    // 댓글 작성 날짜
        private List<Reply> replies;
    }

    @Data
    public static class Reply {
        private int reply_id;
        private int author_id;          // 답글 작성자 ID
        private String text;
        private String reply_date;      // 답글 작성 날짜
    }
}
