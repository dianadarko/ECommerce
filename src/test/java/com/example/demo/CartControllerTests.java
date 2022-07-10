package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CartControllerTests {
    @InjectMocks
    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BigDecimal price = new BigDecimal(158);

    @Before
    public void setup(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"cartRepository",cartRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);

        User user= new User();
        user.setUsername("diana");
        user.setId(33);
        user.setCart(new Cart());
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        Item items= new Item();
        items.setId(33L);
        items.setName("skort");
        items.setDescription("skirt + short");
        items.setPrice(price);
        when(itemRepository.findById(anyLong())).thenReturn(java.util.Optional.of(items));
    }

    @Test
    public void verify_item_addition(){
        ModifyCartRequest modifyCartRequest=new ModifyCartRequest();
        modifyCartRequest.setItemId(33L);
        modifyCartRequest.setQuantity(42);
        modifyCartRequest.setUsername("diana");
        ResponseEntity<Cart> response= cartController.addToCart(modifyCartRequest);
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart()
    {
        ModifyCartRequest modifyCartRequest=new ModifyCartRequest();
        modifyCartRequest.setItemId(33L);
        modifyCartRequest.setQuantity(42);
        modifyCartRequest.setUsername("diana");
        ResponseEntity<Cart> response= cartController.removeFromCart(modifyCartRequest);
        assertEquals(200,response.getStatusCodeValue());

    }

}