package com.ecommerce.dao;

import com.ecommerce.model.Customer;
import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class CustomerDAO {
	static Scanner sc = new Scanner(System.in);
    public void addCustomer(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(customer);
            tx.commit();
            System.out.println("Customer added successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteCustomer(int customerId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Customer customer = session.get(Customer.class, customerId);
            if (customer != null) {
                session.delete(customer);
                System.out.println("Customer deleted successfully!");
            } else {
                System.out.println("Customer not found!");
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    
    public void updateCustomer(int customerId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, customerId);
            if (customer != null) {
            	System.out.println("Current Name: " + customer.getName());
                System.out.print("Do you want to change name? (yes/no): ");
                String nameChoice = sc.nextLine();

                if (nameChoice.equalsIgnoreCase("yes")) {
                    System.out.print("Enter new name: ");
                    String uName = sc.nextLine();
                    customer.setName(uName);
                }

                System.out.println("Current Email: " + customer.getEmail());
                System.out.print("Do you want to change email? (yes/no): ");
                String emailChoice = sc.nextLine();

                if (emailChoice.equalsIgnoreCase("yes")) {
                    System.out.print("Enter new email: ");
                    String uEmail = sc.nextLine();
                    customer.setEmail(uEmail);
                }

                session.update(customer);
                System.out.println("✅ Customer updated successfully!");
            } else {
                System.out.println("⚠️ Customer not found!");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Customer> list = session.createQuery("from Customer").list();
        session.close();
        return list;
    }
}
