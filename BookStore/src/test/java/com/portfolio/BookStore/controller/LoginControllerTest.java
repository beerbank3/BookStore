package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.Dto.UserDto;
import com.portfolio.BookStore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoginControllerTest {

    @Autowired UserService userService;

    @Test
    public void SignProcessTest() throws Exception{
        //given
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setPassword("password");
        userDto.setNickname("nickname");

        //when
        userService.signup(userDto);

        //then
    }
}