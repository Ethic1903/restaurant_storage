package ru.georgiy.restaurant_storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.georgiy.restaurant_storage.models.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findRestaurantByAddressStartingWith(String pattern);

    Optional<Restaurant> findRestaurantByAddress(String address);
}
