package com.example.orderapplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import java.util.List;

@RestController
public class OrderController {
//    private static ParameterizedTypeReference<Product> productType = new ParameterizedTypeReference<Product>() {
//    };

    private OrderService orderService;

    private RestOperations restOperations;

    @Autowired
    public OrderController(RestOperations restOperations, OrderService orderService) {
        this.restOperations = restOperations;
        this.orderService = orderService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Order getOneOrder(@PathVariable Long id) {
        return orderService.findById(id);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String orderNotFound(OrderNotFoundException ex){
        return "No order found!";
    }

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody Order order){
        String url = "http://localhost:8080/product/";
        Product product = restOperations.getForObject(url+order.getProductId(), Product.class);
//        Product product = restOperations.exchange(url+order.getProductId(), HttpMethod.GET,null, productType).getBody();
        orderService.save(order);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> update(@PathVariable long id, @RequestBody Order order){
        Order oldOrder = orderService.findById(id);
        oldOrder.setCustomerName(order.getCustomerName());
        oldOrder.setProductId(order.getProductId());
        orderService.save(oldOrder);
        return new ResponseEntity<>(oldOrder,HttpStatus.OK);

    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<Order> delete(@PathVariable long id) {
        Order order = orderService.findById(id);
        orderService.delete(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
