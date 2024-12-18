package ru.georgiy.restaurant_storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.georgiy.restaurant_storage.models.Product;

@Component
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveProduct(int restId, Product product) {
        jdbcTemplate.update("insert into products (item_name, restaurant_id, amount) values (?, ?, ?)", product.getItemName(), restId, product.getAmount());
    }
}
