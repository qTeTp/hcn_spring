package com.hcn.hcn.service;

import com.hcn.hcn.model.WebUserData;
import com.hcn.hcn.repository.WebUserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebUserDataService {

    @Autowired
    private WebUserDataRepository webUserDataRepository; //레파지토리

    public WebUserData getUserDataByUserId(String userId) {
        return webUserDataRepository.findByUserId(userId);
    }
}
