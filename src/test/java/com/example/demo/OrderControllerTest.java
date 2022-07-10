package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepository=mock(UserRepository.class);
    private OrderRepository orderRepository=mock(OrderRepository.class);

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);

        Item items= new Item();
        items.setId(33L);
        items.setName("shirt");
        items.setDescription("a shirt");
        items.setPrice(BigDecimal.valueOf(158));
        List<Item> itemsList=new ArrayList<>();
        itemsList.add(items);
        Cart cart = new Cart();
        cart.setId(33L);
        cart.setTotal(BigDecimal.valueOf(158));
        cart.setItems(itemsList);
        User user = new User();
        user.setUsername("diana");
        user.setId(22);
        user.setCart(cart);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
    }

    @Test
    public void submit_order() {
        ResponseEntity<UserOrder> userOrder= orderController.submit("diana");
        assertEquals(200,userOrder.getStatusCodeValue());
    }

    @Test
    public void getOrdersForUser_test(){
        ResponseEntity<List<UserOrder>> ordersList =orderController.getOrdersForUser("diana");
        assertEquals(200,ordersList.getStatusCodeValue());
    }


}
