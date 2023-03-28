package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.domain.Order;
import com.portfolio.BookStore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/bookorder")
    public String BookOrder(Model model, @RequestParam(required = false, name = "isbn") String isbn, @RequestParam(required = false, name = "count") Integer count) throws Exception{
        if(null == isbn || isbn.isEmpty()){
            return "redirect:/";
        }
        if(null == count || count == 0){
            return "redirect:/";
        }

        orderService.order(isbn,count);

        return "redirect:/order/orderList";
    }

    @GetMapping("/orderList")
    public String OrderList(Model model) throws Exception{

        List<Order> list = orderService.findOrders();

        model.addAttribute("list",list);

        return "/order/orderList";
    }
}
