package com.github.halab4dev.service;

import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{

    @Override
    public String createToken() {
        return "TokenServiceImpl.createToken()";
    }
}
