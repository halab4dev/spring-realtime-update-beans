package com.github.halab4dev.api;

import com.github.halab4dev.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginApiImpl implements LoginApi {

    private final AccountService accountServiceImpl;

    @Override
    public String execute() {
        return accountServiceImpl.getAccount() + "\n" + "LoginApiImpl.execute()";
    }
}
