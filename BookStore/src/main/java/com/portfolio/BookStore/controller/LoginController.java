package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.Dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/api/register")
    public String UserRegister(Model model) throws Exception{
        model.addAttribute("userDto", new UserDto());
        return "api/register";
    }
}
