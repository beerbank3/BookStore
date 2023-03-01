package com.portfolio.BookStore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Book{

    @Id
    private String isbn;
    private String title;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String contents;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String url;

    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String authors;
    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String translators;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int sale_price;
    @Column(nullable = false)
    private String thumbnail;
    @Column(nullable = false)
    private String status;

}
