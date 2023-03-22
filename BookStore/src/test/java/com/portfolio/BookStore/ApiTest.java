package com.portfolio.BookStore;

import com.portfolio.BookStore.domain.Book;
import com.portfolio.BookStore.repository.BookRepository;
import com.portfolio.BookStore.service.BookService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;


@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class ApiTest {

    @Autowired BookRepository bookRepository;
    @Autowired BookService bookService;

    @Test
    public void weather() throws Exception {

        final String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";
        final String API_KEY = "AbbNHNXkmvK5lCExFYqhkaqIAo4XfqRyoG32doXNFbyqEQ79jtYo%2BKxPK0jK%2FwO81VMDcz3uQqNXeXgTiue%2BXg%3D%3D";

        String serviceKey = API_KEY;
        String pageNo = "1";
        String numOfRows = "12";
        String dataType = "JSON";
        String base_date = "20211020";
        String base_time = "2300";
        String nx = "55";
        String ny = "127";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY); //encoding 모드 설정

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL).build();

        String response = wc.get()
                .uri(uriBuilder -> uriBuilder.path("/getVilageFcst")
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("pageNo", pageNo)
                        .queryParam("dataType", dataType)
                        .queryParam("base_date", base_date)
                        .queryParam("base_time", base_time)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny).build())
                .retrieve().bodyToMono(String.class).block();

        System.out.println(response);
        System.out.println(serviceKey);

    }
    @Test
    public void WebClientApiTest() throws Exception{

        final String BASE_URL = "https://dapi.kakao.com";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL); //UriBuilder를 생성하는 옵션을 설정하는 DefaultUriBuilderFactory 인스턴스 생성
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES); //encoding 모드 설정

        WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(BASE_URL)
                .defaultHeader("Authorization","KakaoAK 3a09703f244c2b8dbfbf567c4771eb0f").build();

        String book = wc.get()
                .uri(uriBuilder -> uriBuilder.path("/v3/search/book")
                        .queryParam("query", "베르나르").build())
                .retrieve().bodyToMono(String.class).block();


        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(book);
        JSONArray jsonArray = (JSONArray) jsonObject.get("documents");

        List<Book> list = new ArrayList<>();
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
        for (Book book1 : list) {
            System.out.println("book1.getTitle() = " + book1.getTitle());
        }
    }
    
    @Test
    public void MainBookList() throws Exception{

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
        for (Book book1 : list) {
            System.out.println("book1.getTitle() = " + book1.getTitle());
        }
    }

}