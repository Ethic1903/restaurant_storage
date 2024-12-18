package ru.georgiy.restaurant_storage.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.georgiy.restaurant_storage.models.Product;
import ru.georgiy.restaurant_storage.models.Restaurant;
import ru.georgiy.restaurant_storage.repositories.ProductsRepository;
import ru.georgiy.restaurant_storage.repositories.RestaurantRepository;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductsRepository productsRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public ProductService(ProductsRepository productsRepository, RestaurantRepository restaurantRepository) {
        this.productsRepository = productsRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Product> findAllProducts() {
        return productsRepository.findAll();
    }

    public Product findOneProduct(int id) {
        return productsRepository.findById(id).orElse(null);
    }

    public void assignProduct(int id, Restaurant restaurant) {
        productsRepository.findById(id).ifPresent(product -> product.setRestaurant(restaurant));
    }

    public Restaurant getClient(int id) {
        return productsRepository.findById(id).map(Product::getRestaurant).orElse(null);
    }

    public void updateProduct(int id, Product updatedProduct) {
        updatedProduct.setId(id);
        productsRepository.save(updatedProduct);
    }

    public void deleteProduct(int id) {
        productsRepository.deleteById(id);
    }

}
