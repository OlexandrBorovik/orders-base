package jpa1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @GeneratedValue(strategy = GenerationType.AUTO)@javax.persistence.Id

    private Long id;

    private int age;
    private String name;

    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


    public Client() {    }

    public Client(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public void addOrder(Order order) {
        orders.add(order);
    }
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name +
                '}';
    }


}
