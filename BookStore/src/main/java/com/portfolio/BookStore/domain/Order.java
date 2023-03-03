package com.portfolio.BookStore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn")
    private Book book;
    private int count;

    private LocalDateTime orderDate;


    public static Order createOrder(Book book, int count){
        Order order = new Order();
        order.setBook(book);
        order.setCount(count);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
}
