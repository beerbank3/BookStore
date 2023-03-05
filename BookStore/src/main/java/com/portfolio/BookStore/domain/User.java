package com.portfolio.BookStore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users")
public class User {

    @Id
    private String userId;

    private String userPw;

    private String userName;

    private String userStatus;

    @Builder
    public User(String userId, String userPw, String userName, String userStatus) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userStatus = userStatus;
    }
}
