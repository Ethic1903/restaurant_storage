package ru.georgiy.restaurant_storage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.georgiy.restaurant_storage.models.Restaurant;
import ru.georgiy.restaurant_storage.services.ProductService;
import ru.georgiy.restaurant_storage.util.ProductValidator;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService, ProductValidator productValidator) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String productInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findOneProduct(id));
        Optional<Restaurant> client = Optional.ofNullable(productService.getClient(id));
        model.addAttribute("client", client.get());

        return "products/product-info";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/storage";
    }
}
