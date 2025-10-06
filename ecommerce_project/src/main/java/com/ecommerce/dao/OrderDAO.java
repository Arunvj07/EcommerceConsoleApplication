package com.ecommerce.dao;


import com.ecommerce.model.Order;

import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class OrderDAO {
    public void placeOrder(Order order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(order);
            tx.commit();
            System.out.println("Order placed successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void cancelOrder(int orderId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Order order = session.get(Order.class, orderId);
            if (order != null) {
                session.delete(order);
                System.out.println("Order canceled successfully!");
            } else {
                System.out.println("Order not found!");
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Order> list = session.createQuery("from Order").list();
        session.close();
        return list;
    }
}
