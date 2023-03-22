package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.domain.Book;
import com.portfolio.BookStore.service.BookService;
import com.portfolio.BookStore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bm")
public class BookManageController {

    private final BookService bookService;
    private final OrderService orderService;

    @RequestMapping("/login")
    public String bmLogin(){
        return "/bm/login";
    }
    @RequestMapping("/main")
    public String bookmanage(Model model){

        Integer BookSalesRate = orderService.findBookSalesRate();//책 총 판매량
        Integer TotalBookSales = orderService.findTotalBookSales();//책 총 판매액수
        Integer CountAllBook = bookService.countByBooks();// 책 총 갯수

        model.addAttribute("BookSalesRate",BookSalesRate);
        model.addAttribute("TotalBookSales",TotalBookSales);
        model.addAttribute("CountAllBook",CountAllBook);

        return "/bm/index";
    }

    @RequestMapping("/tables")
    public String bookTables(Model model){
        List<Book> bookList = bookService.findBooks();

        model.addAttribute("bookList",bookList);

        return "/bm/tables";
    }
}
