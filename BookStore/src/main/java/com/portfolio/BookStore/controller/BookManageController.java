package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.service.BookService;
import com.portfolio.BookStore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigInteger;

@Controller
@RequiredArgsConstructor
public class BookManageController {

    private final BookService bookService;
    private final OrderService orderService;

    @RequestMapping("/bm/main")
    public String bookmanage(Model model){

        Integer BookSalesRate = orderService.findBookSalesRate();//책 총 판매량
        Integer TotalBookSales = orderService.findTotalBookSales();//책 총 판매액수

        model.addAttribute("BookSalesRate",BookSalesRate);
        model.addAttribute("TotalBookSales",TotalBookSales);

        return "index";
    }
}
