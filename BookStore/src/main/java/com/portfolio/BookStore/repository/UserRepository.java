package com.portfolio.BookStore.repository;

import com.portfolio.BookStore.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")//eager조회
    Optional<User> findOneWithAuthoritiesByusername(String username);

    Optional<Object> findOneWithAuthoritiesByUsername(String username);
}
