package com.hcn.hcn.service;

import com.hcn.hcn.model.SendProposal;
import com.hcn.hcn.repository.SendProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendProposalService {

    @Autowired
    private SendProposalRepository sendProposalRepository;

    public SendProposal saveSendProposal(SendProposal sendProposal) {
        return sendProposalRepository.save(sendProposal);
    }

    public List<SendProposal> getProposalsByHeaderId(Long headerId) {
        return sendProposalRepository.findByHeaderId(headerId);
    }

    public SendProposal getSendProposalById(Long id) {
        return sendProposalRepository.findById(id).orElse(null);
    }
}
