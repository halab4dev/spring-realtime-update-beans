package com.github.halab4dev.api;

import com.github.halab4dev.service.AccountService;
import com.github.halab4dev.service.TokenService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginApiFixed implements LoginApi {

    private final AccountService accountServiceImpl;
    private final TokenService tokenServiceImpl;

    @Override
    public String execute() {
        return accountServiceImpl.getAccount() + "\n" + tokenServiceImpl.createToken() + "\n" + "LoginApiFixed.execute()";
    }
}
