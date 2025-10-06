package com.ecommerce.dao;

import com.ecommerce.model.Product;
import java.util.Scanner;
import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProductDAO {
	static Scanner sc = new Scanner(System.in);
    public void addProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteProduct(int productId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Product product = session.get(Product.class, productId);
            if (product != null) {
                session.delete(product);
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product not found!");
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateProduct(int productId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Product product = session.get(Product.class, productId);
            if (product != null) {
            	 System.out.println("Current Name: " + product.getName());
                 System.out.print("Do you want to change name? (yes/no): ");
                 String nameChoice = sc.nextLine();

                 if (nameChoice.equalsIgnoreCase("yes")) {
                     System.out.print("Enter new name: ");
                     String uName = sc.nextLine();
                     product.setName(uName);
                 }

                 System.out.println("Current Price: " + product.getPrice());
                 System.out.print("Do you want to change price? (yes/no): ");
                 String priceChoice = sc.nextLine();

                 if (priceChoice.equalsIgnoreCase("yes")) {
                     System.out.print("Enter new price: ");
                     double uPrice = sc.nextDouble();
                     sc.nextLine(); // clear newline
                     product.setPrice(uPrice);
                 }

                 System.out.println("Current Quantity: " + product.getQuantity());
                 System.out.print("Do you want to change quantity? (yes/no): ");
                 String qtyChoice = sc.nextLine();

                 if (qtyChoice.equalsIgnoreCase("yes")) {
                     System.out.print("Enter new quantity: ");
                     int newQty = sc.nextInt();
                     sc.nextLine(); // clear newline
                     product.setQuantity(newQty);
                 }

                 session.update(product);
                 System.out.println("✅ Product updated successfully!");
            } else {
                System.out.println("⚠️ Product not found!");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public List<Product> getAllProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> list = session.createQuery("from Product").list();
        session.close();
        return list;
    }

    public Product getProductById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product p = (Product) session.get(Product.class, id);
        session.close();
        return p;
    }

    public void updateProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(product);
            tx.commit();
            System.out.println("Product updated successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
