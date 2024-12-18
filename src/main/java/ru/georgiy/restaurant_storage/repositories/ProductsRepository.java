package ru.georgiy.restaurant_storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.georgiy.restaurant_storage.models.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

}
