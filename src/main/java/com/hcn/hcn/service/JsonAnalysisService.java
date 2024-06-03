package com.hcn.hcn.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcn.hcn.model.Account;
import com.hcn.hcn.model.AccountSummary;
import com.hcn.hcn.model.FollowersAgeRange;
import com.hcn.hcn.model.GenderRatio;
import com.hcn.hcn.model.InstagramData;
import com.hcn.hcn.repository.AccountSummaryRepository;
import com.hcn.hcn.repository.FollowersAgeRangeRepository;
import com.hcn.hcn.repository.GenderRatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class JsonAnalysisService {

    @Autowired
    private AccountSummaryRepository accountSummaryRepository;

    @Autowired
    private FollowersAgeRangeRepository followersAgeRangeRepository;

    @Autowired
    private GenderRatioRepository genderRatioRepository;

    public void analyze(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일을 읽고 InstagramData 객체로 변환
            InstagramData instagramData = objectMapper.readValue(new File(filePath), InstagramData.class);

            // 데이터베이스에 저장할 요약 정보 생성
            for (Account account : instagramData.getAccounts()) {
                account.calculateCommentAndReplyCounts();

                AccountSummary summary = new AccountSummary();
                summary.setUsername(account.getUsername());
                summary.setAnalysisDate(LocalDate.now());
                summary.setFollowers(account.getFollowers());
                summary.setProfileViews(account.getProfileViews());
                summary.setAllLikes(account.getAllLikes());
                summary.setCommentCount(account.getCommentCount());
                summary.setReplyCount(account.getReplyCount());

                accountSummaryRepository.save(summary);

                FollowersAgeRange ageRange = new FollowersAgeRange();
                ageRange.setUsername(account.getUsername());
                ageRange.setAnalysisDate(LocalDate.now());
                ageRange.setAge13_17(account.getFollowers_age_range().getOrDefault("13-17", 0));
                ageRange.setAge18_24(account.getFollowers_age_range().getOrDefault("18-24", 0));
                ageRange.setAge25_34(account.getFollowers_age_range().getOrDefault("25-34", 0));
                ageRange.setAge35_44(account.getFollowers_age_range().getOrDefault("35-44", 0));
                ageRange.setAge45_54(account.getFollowers_age_range().getOrDefault("45-54", 0));
                ageRange.setAge55_64(account.getFollowers_age_range().getOrDefault("55-64", 0));
                ageRange.setAge65_plus(account.getFollowers_age_range().getOrDefault("65+", 0));
                ageRange.setAccountSummary(summary);

                followersAgeRangeRepository.save(ageRange);

                GenderRatio genderRatio = new GenderRatio();
                genderRatio.setUsername(account.getUsername());
                genderRatio.setAnalysisDate(LocalDate.now());
                genderRatio.setMale(account.getMalePercentage());
                genderRatio.setFemale(account.getFemalePercentage());
                genderRatio.setAccountSummary(summary);

                genderRatioRepository.save(genderRatio);
            }

            System.out.println("Data saved to database successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
