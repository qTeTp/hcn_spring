package com.hcn.hcn.repository;

import com.hcn.hcn.model.AccountSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountSummaryRepository extends JpaRepository<AccountSummary, Long> {
    List<AccountSummary> findByUsername(String username);
}
