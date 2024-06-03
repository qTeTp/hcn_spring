package com.hcn.hcn.controller;

import com.hcn.hcn.model.AccountSummary;
import com.hcn.hcn.model.FollowersAgeRange;
import com.hcn.hcn.model.GenderRatio;
import com.hcn.hcn.repository.AccountSummaryRepository;
import com.hcn.hcn.repository.FollowersAgeRangeRepository;
import com.hcn.hcn.repository.GenderRatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountSummaryController {

    @Autowired
    private AccountSummaryRepository accountSummaryRepository;

    @Autowired
    private FollowersAgeRangeRepository followersAgeRangeRepository;

    @Autowired
    private GenderRatioRepository genderRatioRepository;

    @GetMapping("/account-summaries/{username}")
    public Map<String, Object> getAccountSummaries(@PathVariable("username") String username) {
        List<AccountSummary> accountSummaries = accountSummaryRepository.findByUsername(username);
        List<FollowersAgeRange> followersAgeRanges = followersAgeRangeRepository.findByUsername(username);
        List<GenderRatio> genderRatios = genderRatioRepository.findByUsername(username);

        Map<String, Object> response = new HashMap<>();
        response.put("accountSummaries", accountSummaries);
        response.put("followersAgeRanges", followersAgeRanges);
        response.put("genderRatios", genderRatios);

        return response;
    }

    @GetMapping("/account-summaries")
    public List<AccountSummary> getAllAccountSummaries() {
        return accountSummaryRepository.findAll();
    }

}
