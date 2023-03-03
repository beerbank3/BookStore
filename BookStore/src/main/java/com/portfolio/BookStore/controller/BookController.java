package com.portfolio.BookStore.controller;


import com.portfolio.BookStore.domain.Book;
import com.portfolio.BookStore.domain.Order;
import com.portfolio.BookStore.repository.OrderRepository;
import com.portfolio.BookStore.repository.OrderSearch;
import com.portfolio.BookStore.service.BookService;
import com.portfolio.BookStore.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final OrderService orderService;

    @GetMapping("/book/bookList")
    public String BookList(Model model , @RequestParam(required = false, name = "category") String category, @RequestParam(required = false, name = "keyword") String key
    ,@RequestParam(required = false, name = "page") Integer page,@RequestParam(required = false, name = "size") Integer size) throws Exception{

        String keyword;
        String target;
        Integer bookpage;
        Integer booksize;
        if(key != null && !key.isEmpty()) {
            keyword = key;
        }else{
            keyword = "";
        }
        if(category != null && !category.isEmpty()) {
            target = category;
        }else{
            target = "";
        }
        if(null != page && 0 != page){
            bookpage = page;
        }else{
            bookpage = 1;
        }
        if(null != size && 0 != size){
            booksize = size;
        }else{
            booksize = 10;
        }

        final String BASE_URL = "https://dapi.kakao.com";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES); //encoding 모드 설정

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL)
                .defaultHeader("Authorization","KakaoAK 3a09703f244c2b8dbfbf567c4771eb0f").defaultHeader("Authorization","KakaoAK 3a09703f244c2b8dbfbf567c4771eb0f").build();

        String book = "";
        List<Book> list = new ArrayList<>();
        if(!keyword.isEmpty()) {
            book = wc.get()
                    .uri(uriBuilder -> uriBuilder.path("/v3/search/book")
                            .queryParam("target", target)
                            .queryParam("query", keyword)
                            .queryParam("page", bookpage)
                            .queryParam("size", booksize).build())
                    .retrieve().bodyToMono(String.class).block();


            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(book);
            JSONArray jsonArray = (JSONArray) jsonObject.get("documents");

            for (Object o : jsonArray) {
                String authors = "";
                String translators = "";
                JSONObject bookObject =(JSONObject) o;
                Book bookDto = new Book();
                bookDto.setIsbn((String) bookObject.get("isbn"));
                bookDto.setTitle((String) bookObject.get("title"));
                bookDto.setContents((String) bookObject.get("contents"));
                bookDto.setUrl((String) bookObject.get("url"));
                bookDto.setDateTime((LocalDateTime) bookObject.get("dateTime"));
                JSONArray authors_list = (JSONArray) bookObject.get("authors");
                for (Object o1 : authors_list) {
                    authors += o1.toString();
                }
                bookDto.setAuthors(authors);
                bookDto.setPublisher((String) bookObject.get("publisher"));
                JSONArray translators_list = (JSONArray) bookObject.get("translators");
                for (Object o1 : translators_list) {
                    translators += o1.toString();
                }
                bookDto.setTranslators(translators);
                bookDto.setPrice(Integer.parseInt(String.valueOf(bookObject.get("price"))));
                bookDto.setSale_price(Integer.parseInt(String.valueOf(bookObject.get("sale_price"))));
                bookDto.setThumbnail((String) bookObject.get("thumbnail"));
                bookDto.setStatus((String) bookObject.get("status"));
                list.add(bookDto);
                bookService.join(bookDto);
            }
        }
        model.addAttribute("list",list);

        return "book/bookList";
    }
    @GetMapping("/book/bookForm")
    public String BookForm(Model model,@RequestParam(required = false, name = "isbn") String isbn) throws Exception{
        if(null == isbn || isbn.isEmpty()){
            return "redirect:/";
        }
        Book book = bookService.findOne(isbn);

        model.addAttribute("book",book);

        return "book/bookForm";
    }

    @PostMapping("/bookorder")
    public String BookOrder(Model model,@RequestParam(required = false, name = "isbn") String isbn,@RequestParam(required = false, name = "count") Integer count) throws Exception{
        if(null == isbn || isbn.isEmpty()){
            return "redirect:/";
        }
        if(null == count || count == 0){
            return "redirect:/";
        }

        orderService.order(isbn,count);

        return "redirect:/order/orderList";
    }

    @GetMapping("/order/orderList")
    public String OrderList(Model model) throws Exception{

        List<Order> list = orderService.findOrders();

        model.addAttribute("list",list);

        return "/order/orderList";
    }
    @Data
    static class BookDto{
        private String isbn;
        private String title;
        private String contents;
        private String url;
        private LocalDateTime dateTime;
        private List<String> authors;
        private String publisher;
        private List<String> translators;
        private int price;
        private int sale_price;
        private String thumbnail;
        private String status;

        public BookDto(Book book) {
            this.authors = authors;
            this.contents = contents;
            this.dateTime = dateTime;
            this.isbn = isbn;
            this.price = price;
            this.publisher = publisher;
            this.sale_price = sale_price;
            this.status = status;
            this.thumbnail = thumbnail;
            this.title = title;
            this.translators = translators;
            this.url = url;
        }
    }
}
