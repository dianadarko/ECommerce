package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
    @Mock
    private UserRepository userRepo =mock(UserRepository.class);
    @Mock
    private CartRepository cartRepo =mock(CartRepository.class);

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository",userRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder",encoder);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
    }

    @Test
    public void createUserTest(){
        when(encoder.encode("testthistest")).thenReturn("isHashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("diana");
        createUserRequest.setPassword("testthistest");
        createUserRequest.setConfirmPassword("testthistest");
        final ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assert user != null;
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("diana", user.getUsername());
        assertEquals("isHashed", user.getPassword());
    }

    @Test
    public void checkPasswordSpecification(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("diana");
        createUserRequest.setPassword("1234abcd");
        createUserRequest.setConfirmPassword("1234abcd");
        final ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void test_find_by_userName(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("diana");
        createUserRequest.setPassword("1234abcd");
        createUserRequest.setConfirmPassword("1234abcd");
        User user= new User();
        user.setUsername("diana");
        user.setId(33);
        when(userRepo.findByUsername(Matchers.anyString())).thenReturn(user);
        ResponseEntity<User> userRepsonse= userController.findByUserName("diana");
        assertEquals("diana",userRepsonse.getBody().getUsername());

    }

    @Test
    public void test_find_by_userId(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("diana");
        createUserRequest.setPassword("1234abcd");
        createUserRequest.setConfirmPassword("1234abcd");
        User user= new User();
        user.setUsername("diana");
        user.setId(33L);
        when(userRepo.findById(Matchers.anyLong())).thenReturn(java.util.Optional.of(user));
        ResponseEntity<User> userRepsonse= userController.findById(33L);
        assertEquals(33L,userRepsonse.getBody().getId());

    }


}
