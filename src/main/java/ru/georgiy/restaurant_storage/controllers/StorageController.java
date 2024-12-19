package ru.georgiy.restaurant_storage.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.georgiy.restaurant_storage.dao.ProductDAO;
import ru.georgiy.restaurant_storage.models.Product;
import ru.georgiy.restaurant_storage.models.Restaurant;
import ru.georgiy.restaurant_storage.services.RestaurantService;
import ru.georgiy.restaurant_storage.util.ProductValidator;
import ru.georgiy.restaurant_storage.util.RestaurantValidator;

@Controller
@RequestMapping("/storage")
public class StorageController {
    private final RestaurantService restaurantService;
    private final ProductDAO productDAO;
    private final ProductValidator productValidator;
    private final RestaurantValidator restaurantValidator;

    @Autowired
    public StorageController(RestaurantService restaurantService, ProductDAO productDAO, ProductValidator productValidator, RestaurantValidator restaurantValidator) {
        this.restaurantService = restaurantService;
        this.productDAO = productDAO;
        this.productValidator = productValidator;
        this.restaurantValidator = restaurantValidator;
    }

    @GetMapping()
    public String storagePage(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "storage-page";
    }

    @GetMapping("/new")
    public String newRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        return "new-restaurant";
    }

    @PostMapping("/new")
    public String addNewRestaurant(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                                   BindingResult bindingResult) {
        restaurantValidator.validate(restaurant, bindingResult);
        if (bindingResult.hasErrors())
            return "new-restaurant";
        restaurantService.saveRestaurant(restaurant);
        return "redirect:/storage";
    }

    @GetMapping("/{id}")
    public String restaurantPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("restaurant", restaurantService.findOne(id));
        model.addAttribute("products", restaurantService.findProductByRestaurantId(id));
        return "restaurant-page";
    }

    @GetMapping("/{id}/order")
    public String showAddProductForm(@ModelAttribute("product") Product product,
                                     @PathVariable("id") int id, Model model) {

        model.addAttribute("product", product);
        model.addAttribute("restaurantId", id);
        return "new-order-page";
    }

    @PostMapping("/{id}/order")
    public String saveProduct(@PathVariable("id") int id,
                              @ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult) {
        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new-order-page";
        }

        productDAO.saveProduct(id, product);
        return "redirect:/storage/" + id;
    }

    @PostMapping()
    public String searchRestaurant(Model model, @RequestParam("query") String query) {
        model.addAttribute("restaurants", restaurantService.findRestaurantByAddressStartingWith(query));
        return "storage-page";
    }

    @GetMapping("/{id}/edit")
    public String changeAddress(Model model, @PathVariable("id") int id) {
        model.addAttribute("restaurant", restaurantService.findOne(id));
        return "change-address";
    }

    @PatchMapping("/{id}")
    public String performChangeAddress(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                                       BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "change-address";
        restaurantService.updateRestaurant(id, restaurant);
        return "redirect:/storage";
    }
}
