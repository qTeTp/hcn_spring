package com.hcn.hcn.service;

import com.hcn.hcn.model.Proposal;
import com.hcn.hcn.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    public Proposal saveProposal(Proposal proposal) {
        return proposalRepository.save(proposal);
    }

    public List<Proposal> getProposalsByUserId(String userId) {
        return proposalRepository.findByUserId(userId);
    }
}
