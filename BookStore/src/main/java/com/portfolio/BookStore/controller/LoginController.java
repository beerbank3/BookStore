package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/bm/login")
    public String Login(Model model){
        return "login";
    }

    @PostMapping("/bm/login")
    public String Login_Process(Model model){
        return "/bm";
    }
}
