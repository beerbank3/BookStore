package com.portfolio.BookStore.repository;

import com.portfolio.BookStore.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) { em.persist(order); }

    public Order findOne(Long id) { return em.find(Order.class, id); }

    public List<Order> findAll(){

        return em.createQuery("select o from Order o join o.book b", Order.class)
                .setMaxResults(1000) //최대 1000건
                .getResultList();
    }

}
