package com.example.orderapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo productRepo) {
        this.orderRepo = productRepo;
    }

    public Order findById(Long id){
        Optional<Order> order = orderRepo.findById(id);
        if (order.isPresent()) {
            return order.get();
        }else{
            throw new OrderNotFoundException();
        }
    }


    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public void save(Order order) {
        orderRepo.save(order);
    }

    public void delete(Order order){ orderRepo.delete(order); }
}
