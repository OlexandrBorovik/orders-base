package jpa1;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {

            emf = Persistence.createEntityManagerFactory("ordersbase");
            em = emf.createEntityManager();


            while (true) {
                System.out.println("1: add client");
                System.out.println("2: add product");
                System.out.println("3: create order");
                System.out.println("4: view clients");
                System.out.println("5: view orders");
                System.out.println("6: view products");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        addClient(sc);
                        break;
                    case "2":
                        addProduct(sc);
                        break;
                    case "3":
                        createOrder(sc);
                        break;
                    case "4":
                        showClients();
                        break;
                    case "5":
                        showOrders();
                        break;
                    case "6":
                        showProducts();
                        break;
                    default:
                        return;
                }
            }
        } finally {
            sc.close();
            em.close();
            emf.close();
        }
    }

    private static void addClient(Scanner sc) {
        System.out.print("Enter clients name ");
        String name = sc.nextLine();
        System.out.print("Enter clients age: ");
        String agec = sc.nextLine();
        int age = Integer.parseInt(agec);

        em.getTransaction().begin();
        try {

            Client c = new Client(age, name);
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void addProduct(Scanner sc) {
        System.out.print("Enter product ");
        String name = sc.nextLine();
        System.out.print("Enter cost: ");
        String cos = sc.nextLine();
        int cost = Integer.parseInt(cos);

        em.getTransaction().begin();
        try {

            Product p = new Product(name, cost);
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();

        }

    }
    private static void showOrders () {
        TypedQuery<Order> typedQuery = em.createQuery("select o from Order o", Order.class);
        List<Order> list = typedQuery.getResultList();
        for (Order o : list) {
            System.out.println(o.toString());
        }
    }
    private static void createOrder(Scanner sc) {
        System.out.print("Enter clients name: ");
        String name = sc.nextLine();
        TypedQuery<Client> typedQuery = em.createQuery("select c from Client c where c.name = :name", Client.class);
        typedQuery.setParameter("name", name);
        Client client = typedQuery.getSingleResult();
        System.out.print("Enter  product: ");
        String productn = sc.nextLine();
        TypedQuery<Product> typedQuery1 = em.createQuery("select p from Product p where p.name = :productn", Product.class);
        typedQuery1.setParameter("productn", productn);
        Product product = typedQuery1.getSingleResult();
        em.getTransaction().begin();
        try {
            Order order = new Order(client,product);
            product.addOrder(order);
            client.addOrder(order);
            em.persist(order);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }
    private static void showClients () {
        Query query = em.createQuery("select x from Client x", Client.class);
        List<Client> list = (List<Client>) query.getResultList();
        for (Client c: list) {
            System.out.println(c.toString());
        }
    }

    private static void showProducts () {
        Query query = em.createQuery("select p from Product p", Product.class);
        List<Product> list = (List<Product>) query.getResultList();
        for (Product p : list) {
            System.out.println(p.toString());
        }
    }

}