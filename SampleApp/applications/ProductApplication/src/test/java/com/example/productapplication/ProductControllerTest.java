package com.example.productapplication;

import com.example.productapplication.ProductController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void getProductThenReturnProduct() throws Exception {
        given(productService.findById(anyLong())).willReturn(new Product("iphone", "cell phone"));
        mockMvc.perform(MockMvcRequestBuilders.get("/product/1")).andExpect(status().isFound())
                .andExpect(jsonPath("productName").value("iphone"))
                .andExpect(jsonPath("productDesc").value("cell phone"));
    }

    @Test
    public void getProductNotFoundThenThrowException() throws Exception {
        given(productService.findById(anyLong())).willThrow(new ProductNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/product/2")).andExpect(status().isNotFound());
    }

    @Test
    public void getAllProductThenReturnProduct() throws Exception {
        List<Product> output = new ArrayList<>();
        output.add(new Product("iphone", "cell phone"));
        output.add(new Product("Chemistry101", "book"));
        given(productService.findAll()).willReturn(output);
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
    public void createProductThenReturnStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Product("iphone", "cell phone"))))
                .andExpect(status().isCreated());

    }

    @Test
    public void updateProductThenReturnProductAndStatus() throws Exception {
        given(productService.findById(anyLong())).willReturn(new Product("haha","ok"));
        mockMvc.perform(MockMvcRequestBuilders.put("/product/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Product("iphone", "cell phone"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("productName").value("iphone"))
                .andExpect(jsonPath("productDesc").value("cell phone"));
    }

    @Test
    public void updateProductNotFoundThrow() throws Exception {
        given(productService.findById(anyLong())).willThrow(new ProductNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.put("/product/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Product("iphone", "cell phone"))))
                .andExpect(status().isNotFound());

    }


    @Test
    public void deleteProductReturnProductandStatus() throws Exception{
        given(productService.findById(anyLong())).willReturn(new Product("iphone", "cell phone"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/1")).andExpect(status().isOk())
                .andExpect(jsonPath("productName").value("iphone"))
                .andExpect(jsonPath("productDesc").value("cell phone"));
    }

    @Test
    public void deleteNotFoundThenThrow() throws Exception{
        given(productService.findById(anyLong())).willThrow(new ProductNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/1")).andExpect(status().isNotFound());
    }







}
