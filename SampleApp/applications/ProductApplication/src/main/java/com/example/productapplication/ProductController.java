package com.example.productapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Product getOneProduct(@PathVariable Long id) {
        return productService.findById(id);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String productNotFound(ProductNotFoundException ex){
        return "No product found!";
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable long id) {
        Product old = productService.findById(id);
        old.setProductDesc(product.getProductDesc());
        old.setProductName(product.getProductName());
        productService.save(old);
        return new ResponseEntity<>(old, HttpStatus.OK);

    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> delete(@PathVariable long id) {
        Product product = productService.findById(id);
        productService.delete(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }






}
