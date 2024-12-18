package ru.georgiy.restaurant_storage.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Это поле не может быть пустым")
    @Column(name = "item_name")
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @NotNull(message = "Это поле не может быть пустым")
//    @Min(value = 1, message = "Минимальное количество 1 единица товара")
    @Column(name = "amount")
    private Integer amount;

    public Product() {
    }

    public Product(String itemName, Integer amount) {
        this.itemName = itemName;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant client) {
        this.restaurant = client;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                ", restaurant=" + (restaurant != null ? restaurant.getId() : "null") +
                '}';
    }

}
