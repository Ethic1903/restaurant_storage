package ru.georgiy.restaurant_storage.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "^[А-Яа-яA-Za-z\\-\\s]+,\\s[А-Яа-яA-Za-z\\-\\s]+,\\s\\d+(?:\\s?к\\d+)?$",
            message = "Неправильный формат")
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products;

    public Restaurant() {
    }

    public Restaurant(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
