package com.example.orderapplication;

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
public class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Autowired
    private OrderService orderService;

    @Before
    public void setUp() throws Exception{
        orderService = new OrderService(orderRepo);
    }

    @Test
    public void getOrderThenReturnOrder() throws Exception {
        given(orderRepo.findById(anyLong())).willReturn(Optional.of(new Order(1, "Jane Doe")));

        Order order = orderService.findById((long) 1);
        assertTrue(order.getProductId() == 1);
        assertTrue(order.getCustomerName() == "Jane Doe");

    }

    @Test(expected = OrderNotFoundException.class)
    public void getOrderNotFoundThenThrowException() throws Exception {
        Optional<Order> order = Optional.empty();
        given(orderRepo.findById(anyLong())).willReturn(order);
        orderService.findById((long) 1);

    }

    @Test
    public void getAllOrderReturnAllOrder() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "Jane Doe"));
        orders.add(new Order(2, "someone"));
        given(orderRepo.findAll()).willReturn(orders);

        List<Order> list = orderService.findAll();
        assertTrue(list.get(0).getProductId() == 1);
        assertTrue(list.get(0).getCustomerName() == "Jane Doe");
        assertTrue(list.get(1).getProductId() == 2);
        assertTrue(list.get(1).getCustomerName() == "someone");
    }


}
