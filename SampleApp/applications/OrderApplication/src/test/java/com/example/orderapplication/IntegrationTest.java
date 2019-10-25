package com.example.orderapplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


//    @Test
//    public void createProductThenReturnProductDetail() {
//        Order input = new Order(1, "Jane Doe");
//        restTemplate.postForEntity("/", input, String.class);
//
//        ResponseEntity<Product> result = restTemplate.exchange("/product/1", HttpMethod.GET, null, Product.class);
//        Product product = result.getBody();
//        System.out.println(product.getProductName());
//        System.out.println(product.getProductDesc());
//        assertEquals(HttpStatus.FOUND, result.getStatusCode());
//        assertEquals("iphone", product.getProductName());
//        assertEquals("cell phone", product.getProductDesc());
////    }
//
//    @Test
//    public void deleteProductReturnNotFound() {
//        Order input = new Order("iphone", "cell phone");
//        restTemplate.postForEntity("/", input, String.class);
//
//        ResponseEntity<Product> deleteResponse = restTemplate.exchange("/product/1",HttpMethod.DELETE,null, Product.class);
//
//        ResponseEntity getResponse = restTemplate.exchange("/product/1", HttpMethod.GET, null, String.class);
//
//        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
//        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
//        assertEquals("No product found!", getResponse.getBody());
//
//    }
//
//    @Test
//    public void updateProductReturnUpdatedProduct() {
//        Product input = new Product("iphone", "cell phone");
//        restTemplate.postForEntity("/", input, String.class);
//
//        Product updateInput = new Product("chemistry", "book");
//        restTemplate.put("/product/1", updateInput);
//
//        ResponseEntity<Product> getResponse = restTemplate.getForEntity("/product/1", Product.class);
//
//        assertEquals(HttpStatus.FOUND, getResponse.getStatusCode());
//        assertEquals("chemistry", getResponse.getBody().getProductName());
//        assertEquals("book", getResponse.getBody().getProductDesc());
//
//
//    }
}
