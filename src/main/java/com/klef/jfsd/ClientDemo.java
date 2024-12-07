package com.klef.jfsd;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.klef.jfsd.entity.Customer;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Customer.class).buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            // Insert records
            session.beginTransaction();
            Customer c1 = new Customer("Alice", "alice@example.com", 25, "New York");
            Customer c2 = new Customer("Bob", "bob@example.com", 30, "Los Angeles");
            Customer c3 = new Customer("Charlie", "charlie@example.com", 35, "Chicago");
            Customer c4 = new Customer("Diana", "diana@example.com", 28, "San Francisco");

            session.save(c1);
            session.save(c2);
            session.save(c3);
            session.save(c4);
            session.getTransaction().commit();

            // Applying Criteria Queries
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Customer.class);

            // Equal Restriction
            System.out.println("\n--- Customers from New York ---");
            criteria.add(Restrictions.eq("location", "New York"));
            List<Customer> result1 = criteria.list();
            result1.forEach(System.out::println);

            // Greater Than Restriction
            System.out.println("\n--- Customers older than 28 ---");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.gt("age", 28));
            List<Customer> result2 = criteria.list();
            result2.forEach(System.out::println);

            // Between Restriction
            System.out.println("\n--- Customers aged between 25 and 30 ---");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.between("age", 25, 30));
            List<Customer> result3 = criteria.list();
            result3.forEach(System.out::println);

            // Like Restriction
            System.out.println("\n--- Customers with names starting with 'A' ---");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.like("name", "A%"));
            List<Customer> result4 = criteria.list();
            result4.forEach(System.out::println);

            session.getTransaction().commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
