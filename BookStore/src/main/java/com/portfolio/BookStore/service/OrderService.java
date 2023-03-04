package com.portfolio.BookStore.service;

import com.portfolio.BookStore.domain.Book;
import com.portfolio.BookStore.domain.Order;
import com.portfolio.BookStore.repository.BookRepository;
import com.portfolio.BookStore.repository.OrderRepository;
import com.portfolio.BookStore.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Long order(String isbn, int count){

        Book book = bookRepository.findOne(isbn);

        Order order = Order.createOrder(book,count);
        orderRepository.save(order);

        return order.getId();
    }
    //책 총 판매량
    public Integer findBookSalesRate(){ return orderRepository.findBookSalesRate(); }
    //책 총 판매액수
    public Integer findTotalBookSales(){ return orderRepository.findTotalBookSales(); }
    public List<Order> findOrders(){
        return orderRepository.findAll();
    }
}
