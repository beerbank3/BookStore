package com.portfolio.BookStore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String member;
    private String isbn;
    private int count;

    public static Order createOrder(String isbn, int count){
        Order order = new Order();
        order.setIsbn(isbn);
        order.setCount(count);

        return order;
    }
}
