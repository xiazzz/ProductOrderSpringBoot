package com.example.productapplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product findById(Long id){
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            return product.get();
        }else{
            throw new ProductNotFoundException();
        }
    }


    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public void save(Product product) {
        productRepo.save(product);
    }

    public void delete(Product product){ productRepo.delete(product); }
}
