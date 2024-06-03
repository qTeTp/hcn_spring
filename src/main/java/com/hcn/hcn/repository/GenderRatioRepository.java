package com.hcn.hcn.repository;

import com.hcn.hcn.model.GenderRatio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenderRatioRepository extends JpaRepository<GenderRatio, Long> {
    List<GenderRatio> findByUsername(String username);
}
