package com.hcn.hcn.repository;

import com.hcn.hcn.model.FollowersAgeRange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowersAgeRangeRepository extends JpaRepository<FollowersAgeRange, Long> {
    List<FollowersAgeRange> findByUsername(String username);
}
