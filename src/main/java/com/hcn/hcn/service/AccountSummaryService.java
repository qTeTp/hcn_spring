package com.hcn.hcn.service;

import com.hcn.hcn.model.AccountSummary;
import com.hcn.hcn.repository.AccountSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountSummaryService {

    @Autowired
    private AccountSummaryRepository accountSummaryRepository;

    public List<AccountSummary> getAllAccountSummaries() {
        return accountSummaryRepository.findAll();
    }

    public AccountSummary getAccountSummaryById(Long id) {
        return accountSummaryRepository.findById(id).orElse(null);
    }

    public AccountSummary createAccountSummary(AccountSummary accountSummary) {
        return accountSummaryRepository.save(accountSummary);
    }

    public AccountSummary updateAccountSummary(Long id, AccountSummary accountSummaryDetails) {
        AccountSummary accountSummary = accountSummaryRepository.findById(id).orElse(null);
        if (accountSummary != null) {
            accountSummary.setUsername(accountSummaryDetails.getUsername());
            accountSummary.setAnalysisDate(accountSummaryDetails.getAnalysisDate());
            accountSummary.setFollowers(accountSummaryDetails.getFollowers());
            accountSummary.setProfileViews(accountSummaryDetails.getProfileViews());
            accountSummary.setAllLikes(accountSummaryDetails.getAllLikes());
            accountSummary.setCommentCount(accountSummaryDetails.getCommentCount());
            accountSummary.setReplyCount(accountSummaryDetails.getReplyCount());
            return accountSummaryRepository.save(accountSummary);
        }
        return null;
    }

    public void deleteAccountSummary(Long id) {
        accountSummaryRepository.deleteById(id);
    }
}
