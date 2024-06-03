package com.hcn.hcn.model;

import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@Table(name = "followers_age_range")
public class FollowersAgeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private LocalDate analysisDate;
    private int age13_17;
    private int age18_24;
    private int age25_34;
    private int age35_44;
    private int age45_54;
    private int age55_64;
    private int age65_plus;

    @ManyToOne
    @JoinColumn(name = "summary_id")
    private AccountSummary accountSummary;
}
