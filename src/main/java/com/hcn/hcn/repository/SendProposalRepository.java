package com.hcn.hcn.repository;

import com.hcn.hcn.model.SendProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendProposalRepository extends JpaRepository<SendProposal, Long> {
    List<SendProposal> findByHeaderId(Long headerId);
}
