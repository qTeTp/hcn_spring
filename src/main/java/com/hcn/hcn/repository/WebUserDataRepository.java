package com.hcn.hcn.repository;

import com.hcn.hcn.model.WebUserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebUserDataRepository extends JpaRepository<WebUserData, Long> {
    WebUserData findByUserId(String userId);
}
