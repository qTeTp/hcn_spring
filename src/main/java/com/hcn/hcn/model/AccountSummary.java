package com.hcn.hcn.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@Table(name = "account_summaries")
public class AccountSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private LocalDate analysisDate;
    private long followers;
    private long profileViews;
    private long allLikes;
    private int commentCount;
    private int replyCount;
}
