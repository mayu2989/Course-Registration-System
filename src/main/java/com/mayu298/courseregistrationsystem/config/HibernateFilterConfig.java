package com.mayu298.courseregistrationsystem.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateFilterConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void enableFilter() {

        Session session = entityManager.unwrap(Session.class);

        session.enableFilter("deletedCourseFilter")
                .setParameter("isDeleted", false);
    }
}