package com.hcn.hcn.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InstagramData {
    @JsonProperty("accounts")
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
