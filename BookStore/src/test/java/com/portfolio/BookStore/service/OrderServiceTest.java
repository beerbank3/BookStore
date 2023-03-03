package com.portfolio.BookStore.service;

import com.portfolio.BookStore.domain.Book;
import com.portfolio.BookStore.domain.Order;
import com.portfolio.BookStore.repository.OrderRepository;
import com.portfolio.BookStore.repository.OrderSearch;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void order() throws Exception{
        //given
        String isbn = "97911344185432";
        Integer count = 1;

        //when
        Long orderId = orderService.order(isbn,count);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 기능 테스트", getOrder.getId(), orderId);
    }
    
    @Test
    public void orderList() throws Exception{
        //given
        orderService.order("1159421951 9791159421952",2);
        //when
        List<Order> order = orderRepository.findAll();
        
        //then
        System.out.println("order.size() = " + order.size());
        for(Order list : order){
            System.out.println("list.toString() = " + list.getBook().getIsbn());
            System.out.println("list.toString() = " + list.getBook().getTitle());
            System.out.println("list.toString() = " + list.getBook().getThumbnail());
            System.out.println("list.toString() = " + list.getBook().getPrice());
            System.out.println("list.toString() = " + list.getBook().getPublisher());
        }
    }

}