package ru.georgiy.restaurant_storage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.georgiy.restaurant_storage.models.Product;
import ru.georgiy.restaurant_storage.services.ProductService;

@Component
public class ProductValidator implements Validator {
    private final ProductService productService;

    @Autowired
    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if (!(product.getAmount() > 0))
            errors.rejectValue("amount", "", "Минимальное количество для заказа - 1 шт.");
    }
}
