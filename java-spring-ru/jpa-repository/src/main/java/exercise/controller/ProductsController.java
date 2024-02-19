package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    private static final String MAX_INT_AS_STRING = Integer.toString(Integer.MAX_VALUE);
    private static final String MIN_INT_AS_STRING = Integer.toString(Integer.MIN_VALUE);
    @GetMapping(path = "")
    public List<Product> sorted(@RequestParam(required = false, defaultValue = MIN_INT_AS_STRING) Integer min,
                                @RequestParam(required = false, defaultValue = MAX_INT_AS_STRING) Integer max) {
        return productRepository.findByPriceBetweenOrderByPrice(min, max);
    }

    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
