package com.example.orderapplication;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestOperations restOperations;

    @MockBean
    private OrderService orderService;

    @Test
    public void getOrderThenReturnOrder() throws Exception {
        given(orderService.findById(anyLong())).willReturn(new Order(1, "Jane Doe"));
        mockMvc.perform(MockMvcRequestBuilders.get("/order/1")).andExpect(status().isFound())
                .andExpect(jsonPath("productId").value(1))
                .andExpect(jsonPath("customerName").value("Jane Doe"));
    }

    @Test
    public void getOrdertNotFoundThenThrowException() throws Exception {
        given(orderService.findById(anyLong())).willThrow(new OrderNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/product/2")).andExpect(status().isNotFound());
    }

    @Test
    public void getAllOrderThenReturnOrder() throws Exception {
        List<Order> output = new ArrayList<>();
        output.add(new Order(1, "Jane Doe"));
        output.add(new Order(2, "Someone"));
        given(orderService.findAll()).willReturn(output);
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createOrderThenReturnProductAndStatus() throws Exception {

        given(restOperations.getForObject(anyString(),any())).willReturn(new Product("iphone", "cell phone"));
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Order(1, "Jane Doe"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("productName").value("iphone"))
                .andExpect(jsonPath("productDesc").value("cell phone"));

    }

    @Test
    public void updateOrderThenReturnOrderAndStatus() throws Exception {
        given(orderService.findById(anyLong())).willReturn(new Order(1, "Jane Doe"));
        mockMvc.perform(MockMvcRequestBuilders.put("/order/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Order(2, "other person"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("productId").value(2))
                .andExpect(jsonPath("customerName").value("other person"));
    }

    @Test
    public void updateOrderNotFoundThrow() throws Exception {
        given(orderService.findById(anyLong())).willThrow(new OrderNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.put("/order/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Order(1, "somesome"))))
                .andExpect(status().isNotFound());

    }


    @Test
    public void deleteOrderReturnOrderandStatus() throws Exception{
        given(orderService.findById(anyLong())).willReturn(new Order(1, "Jane Doe"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1")).andExpect(status().isOk())
                .andExpect(jsonPath("productId").value(1))
                .andExpect(jsonPath("customerName").value("Jane Doe"));
    }

    @Test
    public void deleteNotFoundThenThrow() throws Exception{
        given(orderService.findById(anyLong())).willThrow(new OrderNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1")).andExpect(status().isNotFound());
    }







}
