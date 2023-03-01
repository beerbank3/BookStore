package com.portfolio.BookStore.repository;

import com.portfolio.BookStore.domain.Book;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public void save(Book book) { em.persist(book); }

    public Book findOne(String isbn){ return em.find(Book.class, isbn); }

    public List<Book> findAll() { return em.createQuery("select b from Book b", Book.class).getResultList(); }

    public List<Book> findByIsbn(String isbn){
        return em.createQuery("select b from Book b where b.isbn = :isbn", Book.class).setParameter("isbn",isbn).getResultList();
    }
}
