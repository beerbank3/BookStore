package com.portfolio.BookStore.service;

import com.portfolio.BookStore.domain.Book;
import com.portfolio.BookStore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public String join(Book book){
        if(validateDuplicateBook(book)) {
            bookRepository.save(book);
        }
        return book.getIsbn();
    }
    @Transactional
    public void saveBook(Book book){ bookRepository.save(book);}

    private boolean validateDuplicateBook(Book book){

        Boolean isFindbook = true;
        List<Book> findBooks = bookRepository.findByIsbn(book.getIsbn());
        if(!findBooks.isEmpty()){
            //throw new IllegalStateException("이미 존재하는 책입니다.");
            isFindbook = false;
        }
        return isFindbook;
    }

    public List<Book> findBooks(){ return bookRepository.findAll();}

    public Book findOne(String isbn){ return bookRepository.findOne(isbn); }

    public Integer countByBooks(){ return bookRepository.countByBooks();}


}
