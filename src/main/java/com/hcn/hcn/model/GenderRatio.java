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
@Table(name = "gender_ratio")
public class GenderRatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private LocalDate analysisDate;
    private double male;
    private double female;

    @ManyToOne
    @JoinColumn(name = "summary_id")
    private AccountSummary accountSummary;
}
