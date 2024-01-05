package com.github.halab4dev.service;

import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public String getAccount() {
        return "AccountServiceImpl.getAccount()";
    }
}
