package com.hcn.hcn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class WebUserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 대표 이름
    private String userId; // 아이디
    private String password; //비번
    private String category; //분야

    @ElementCollection
    @CollectionTable(name = "web_user_data_age_groups", joinColumns = @JoinColumn(name = "web_user_data_id"))
    @Column(name = "age_group")
    private List<String> ageGroups;

    private String companyName; //회사 이름
    private String companyPhone; //회사 번호
    private String companyAddress; // 회사 주소
    private String profileImageUrl; // 회사 대표 사진 URL
    private String websiteUrl; // 회사 사이트 URL
    @Column(columnDefinition = "TEXT")
    private String companyDescription; // 회사 소개글
    private float points; // 보유 포인트


    @Override
    public String toString() {
        return "WebUserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", category='" + category + '\'' +
                ", ageGroups=" + ageGroups +
                ", companyName='" + companyName + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", points=" + points +
                '}';
    }
}
