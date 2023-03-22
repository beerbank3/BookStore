package com.portfolio.BookStore.controller;

import com.portfolio.BookStore.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) throws Exception{
        log.info("home controller");
        final String BASE_URL = "http://www.aladin.co.kr";
        final String API_KEY = "ttbdlrwns55651140001";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES); //encoding 모드 설정

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        String book = wc.get()
                .uri(uriBuilder -> uriBuilder.path("/ttb/api/ItemList.aspx")
                        .queryParam("ttbkey", API_KEY)
                        .queryParam("QueryType", "Bestseller")
                        .queryParam("MaxResults", "10")
                        .queryParam("start", "1")
                        .queryParam("SearchTarget", "Book")
                        .queryParam("output", "JS")
                        .queryParam("Version", "20131101").build())
                .retrieve().bodyToMono(String.class).block();


        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(book);
        JSONArray jsonArray = (JSONArray) jsonObject.get("item");

        List<Book> list = new ArrayList<>();
        for (Object o : jsonArray) {
            String authors = "";
            String translators = "";
            JSONObject bookObject =(JSONObject) o;
            Book bookDto = new Book();
            bookDto.setTitle((String) bookObject.get("title"));
            bookDto.setAuthors((String)bookObject.get("author"));
            bookDto.setThumbnail((String) bookObject.get("cover"));
            list.add(bookDto);
        }
        model.addAttribute("list",list);
        return "home";
    }
}
