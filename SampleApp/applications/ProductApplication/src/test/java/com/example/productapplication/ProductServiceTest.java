package com.example.productapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @Before
    public void setUp() throws Exception{
        productService = new ProductService(productRepo);
    }

    @Test
    public void getProductThenReturnProduct() throws Exception {
        given(productRepo.findById(anyLong())).willReturn(java.util.Optional.of(new Product("iphone", "cell phone")));

        Product product = productService.findById((long) 1);
        assertTrue(product.getProductName() == "iphone");
        assertTrue(product.getProductDesc() == "cell phone");

    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductNotFoundThenThrowException() throws Exception {
        Optional<Product> product = Optional.empty();
        given(productRepo.findById(anyLong())).willReturn(product);
        productService.findById((long) 1);

    }

    @Test
    public void getAllProductReturnAllProduct() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product("iphone", "cell phone"));
        products.add(new Product("chemistry101", "book"));
        given(productRepo.findAll()).willReturn(products);

        List<Product> list = productService.findAll();
        assertTrue(list.get(0).getProductName() == "iphone");
        assertTrue(list.get(0).getProductDesc() == "cell phone");
        assertTrue(list.get(1).getProductName() == "chemistry101");
        assertTrue(list.get(1).getProductDesc() == "book");
    }


}
