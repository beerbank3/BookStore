package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.domain.Book;
import com.portfolio.BookStore.service.BookService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookManageControllerTest {

    @Autowired BookService bookService;

    @Test
    public void CountAllBook() throws Exception{
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
        book.setIsbn("13216545");
        String bookid = bookService.join(book);

        //when
        Integer countAllBook = bookService.countByBooks();

        //then
        assertSame("책 갯수", 1,countAllBook);
    }
}