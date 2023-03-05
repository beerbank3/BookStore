package com.portfolio.BookStore.repository;

import com.portfolio.BookStore.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public User findOne(Long userId){ return em.find(User.class, userId); }
}
