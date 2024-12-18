package ru.georgiy.restaurant_storage.services;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.georgiy.restaurant_storage.models.Product;
import ru.georgiy.restaurant_storage.models.Restaurant;
import ru.georgiy.restaurant_storage.repositories.RestaurantRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;


    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll(Sort.by("address"));
    }

    public Restaurant findOne(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void updateRestaurant(int id, Restaurant updatedRestaurant) {
        updatedRestaurant.setId(id);
        restaurantRepository.save(updatedRestaurant);
    }

    public void deleteRestaurant(int id) {
        restaurantRepository.deleteById(id);
    }

    public List<Product> findProductByRestaurantId(int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            Hibernate.initialize(restaurant.get().getProducts());
            return restaurant.get().getProducts();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Restaurant> findRestaurantByAddressStartingWith(String pattern) {
        return restaurantRepository.findRestaurantByAddressStartingWith(pattern);
    }

    public Optional<Restaurant> findRestaurantByAddress(String address) {
        return restaurantRepository.findRestaurantByAddress(address);
    }
}
