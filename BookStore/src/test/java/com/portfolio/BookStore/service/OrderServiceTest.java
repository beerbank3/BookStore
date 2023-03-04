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
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired BookService bookService;

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

    @Test
    public void findBookSalesRate() throws Exception{

        orderService.order("1159421951 9791159421952",2);
        orderService.order("1159421951 9791159123123",6);
        orderService.order("1159421951 97567159123123",5);
        orderService.order("1159421951 9791159113133",124);

        Integer BookSalesRate = orderService.findBookSalesRate();

        assertEquals("책 판매량 갯수 체크", Optional.of(137),BookSalesRate);
    }

    @Test
    public void TotalBookSales() throws Exception{
        //given
        Book book = new Book();
        book.setPrice(10000);
        book.setAuthors("안녕하세요");
        book.setContents("테스트입니다.");
        book.setUrl("url");
        book.setThumbnail("");
        book.setPublisher("");
        book.setStatus("");
        book.setTranslators("");
        book.setIsbn("1159421951");
        bookService.join(book);

        orderService.order("1159421951",2);
        orderService.order("1159421951",6);

        //when
        Integer TotalBookSales = orderService.findTotalBookSales();

        //then
        assertEquals("책 판매액수 체크", Optional.of(80000),TotalBookSales);
    }

}