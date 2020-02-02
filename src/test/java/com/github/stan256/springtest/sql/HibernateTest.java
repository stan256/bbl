package com.github.stan256.springtest.sql;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;


class HibernateTest {
    EntityManager em;

    @BeforeEach
    public void init(){
        System.out.println(1);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "postgres" );
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    public void after(){
        if (em.getTransaction().isActive()){
            em.getTransaction().commit();
        }
        em.getEntityManagerFactory().close();
        em.close();
    }

    @Test
    public void shouldStartHibernatePostgres() {
        em.getTransaction();
    }
}
