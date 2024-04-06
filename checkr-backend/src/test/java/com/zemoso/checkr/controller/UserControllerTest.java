package com.zemoso.checkr.controller;

import com.zemoso.checkr.dto.UserDTO;
import com.zemoso.checkr.exception.UserNotFoundException;
import com.zemoso.checkr.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void testGetUser_Success() throws UserNotFoundException {
        String email = "test@example.com";
        String password = "password";
        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setEmail(email);
        Mockito.when(userService.getUser(email, password)).thenReturn(expectedUserDTO);

        ResponseEntity<?> response = userController.getUser(email, password);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedUserDTO, response.getBody());
    }

    @Test
    void testGetUser_UserNotFoundException() throws UserNotFoundException {
        String email = "nonexistent@example.com";
        String password = "somepassword";

        when(userService.getUser(email, password)).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<Object> responseEntity = userController.getUser(email, password);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User not found", responseEntity.getBody());
    }

    @Test
    void testCreateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");

        when(userService.createUser(userDTO)).thenReturn(userDTO);

        ResponseEntity<Object> responseEntity = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(userDTO, responseEntity.getBody());
    }

    @Test
    void testCreateUser_Exception() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");

        when(userService.createUser(userDTO)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<Object> responseEntity = userController.createUser(userDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().toString().contains("Internal Server Error"));
    }

    @Test
    void testUpdateUser_Success() throws UserNotFoundException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("newpassword123");

        when(userService.updateUser(userDTO)).thenReturn(userDTO);

        ResponseEntity<Object> responseEntity = userController.updateUser(userDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDTO, responseEntity.getBody());
    }

    @Test
    void testUpdateUser_Exception() throws UserNotFoundException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("newpassword123");

        when(userService.updateUser(userDTO)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<Object> responseEntity = userController.updateUser(userDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().toString().contains("Internal Server Error"));
    }
}