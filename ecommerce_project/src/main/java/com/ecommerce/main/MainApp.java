package com.ecommerce.main;

import com.ecommerce.dao.*;
import com.ecommerce.model.*;
import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO();

        int choice = 0;
        while (choice != 12) {
            System.out.println("\n==== ECOMMERCE MENU ====");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Product");
            System.out.println("3. Place Order");
            System.out.println("4. View Customers");
            System.out.println("5. View Products");
            System.out.println("6. View Orders");
            System.out.println("7. Delete Customer");
            System.out.println("8. Delete Product");
            System.out.println("9. Cancel Order");
            System.out.println("10. Update Customer");
            System.out.println("11. update Product");
            System.out.println("12. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String cname = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    customerDAO.addCustomer(new Customer(cname, email));
                    break;

                case 2:
                    System.out.print("Enter product name: ");
                    String pname = sc.nextLine();
                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    productDAO.addProduct(new Product(pname, price, qty));
                    break;

                case 3:
                    List<Customer> customers = customerDAO.getAllCustomers();
                    List<Product> products = productDAO.getAllProducts();

                    if (customers.isEmpty() || products.isEmpty()) {
                        System.out.println("Add customers and products first!");
                        break;
                    }

                    System.out.println("\nCustomers:");
                    for (Customer c : customers)
                        System.out.println(c.getId() + ". " + c.getName());

                    System.out.print("Select customer ID: ");
                    int cid = sc.nextInt();

                    System.out.println("\nProducts:");
                    for (Product p : products)
                        System.out.println(p.getId() + ". " + p.getName() + " - Rs." + p.getPrice() + " | Qty: " + p.getQuantity());

                    System.out.print("Select product ID: ");
                    int pid = sc.nextInt();
                    System.out.print("Enter quantity: ");
                    int oqty = sc.nextInt();

                    Customer selectedCustomer = null;
                    Product selectedProduct = null;

                    for (Customer c : customers) {
                        if (c.getId() == cid) {
                            selectedCustomer = c;
                            break;
                        }
                    }
                    for (Product p : products) {
                        if (p.getId() == pid) {
                            selectedProduct = p;
                            break;
                        }
                    }

                    if (selectedCustomer != null && selectedProduct != null) {
                        if (selectedProduct.getQuantity() >= oqty) {
                            selectedProduct.setQuantity(selectedProduct.getQuantity() - oqty);
                            productDAO.updateProduct(selectedProduct);
                            orderDAO.placeOrder(new Order(selectedCustomer, selectedProduct, oqty));
                        } else {
                            System.out.println("Not enough stock available!");
                        }
                    } else {
                        System.out.println("Invalid selection!");
                    }
                    break;

                case 4:
                    System.out.println("\nCustomers:");
                    for (Customer c : customerDAO.getAllCustomers())
                        System.out.println(c.getId() + " | " + c.getName() + " | " + c.getEmail());
                    break;

                case 5:
                    System.out.println("\nProducts:");
                    for (Product p : productDAO.getAllProducts())
                        System.out.println(p.getId() + " | " + p.getName() + " | Rs." + p.getPrice() + " | Qty: " + p.getQuantity());
                    break;

                case 6:
                    System.out.println("\nOrders:");
                    for (Order o : orderDAO.getAllOrders())
                        System.out.println("Order#" + o.getId() + " | Customer: " + o.getCustomer().getName() +
                                " | Product: " + o.getProduct().getName() + " | Qty: " + o.getQuantity());
                    break;
                    
                case 7:
                    System.out.print("Enter customer ID to delete: ");
                    int delCid = sc.nextInt();
                    customerDAO.deleteCustomer(delCid);
                    break;

                case 8:
                    System.out.print("Enter product ID to delete: ");
                    int delPid = sc.nextInt();
                    productDAO.deleteProduct(delPid);
                    break;

                case 9:
                    System.out.print("Enter order ID to cancel: ");
                    int delOid = sc.nextInt();
                    orderDAO.cancelOrder(delOid);
                    break;
            
                
                case 10:
                    System.out.print("Enter Customer ID to update: ");
                    int updatecid = sc.nextInt();
                    sc.nextLine();
                    customerDAO.updateCustomer(updatecid);
                    break;

                case 11:
                    System.out.print("Enter Product ID to update: ");
                    int updatepid = sc.nextInt();
                    sc.nextLine();
                    productDAO.updateProduct(updatepid);
                    break;
     
                    
                case 12:
                    System.out.println("Exiting...");
                    break;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}
