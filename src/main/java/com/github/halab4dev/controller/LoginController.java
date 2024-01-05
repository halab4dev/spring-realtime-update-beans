package com.github.halab4dev.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.halab4dev.api.LoginApi;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginApi loginApi;

    @PostMapping
    public String test() {
        return loginApi.execute();
    }
}
