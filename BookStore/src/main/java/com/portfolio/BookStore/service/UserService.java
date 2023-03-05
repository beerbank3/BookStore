package com.portfolio.BookStore.service;


import com.portfolio.BookStore.domain.User;

import com.portfolio.BookStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOne(Long userId){
        return userRepository.findOne(userId);
    }
}
