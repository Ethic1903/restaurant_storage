package ru.georgiy.restaurant_storage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.georgiy.restaurant_storage.models.Restaurant;
import ru.georgiy.restaurant_storage.services.RestaurantService;

@Component
public class RestaurantValidator implements Validator {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantValidator(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = (Restaurant) target;
        if (restaurantService.findRestaurantByAddress(restaurant.getAddress()).isPresent())
            errors.rejectValue("address", "", "Ресторан с этим адресом уже внесен в реестр");
    }
}
