package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.UserDTO;
import com.zemoso.checkr.entities.User;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.exception.UserNotFoundException;
import com.zemoso.checkr.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = new User("test@example.com", "password");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(new UserDTO("test@example.com", "password"));
    }

    @Test
    void testGetUser_Success() throws UserNotFoundException {
        String email = "test@example.com";
        String password = "correctpassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserDTO result = userService.getUser(email, password);

        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    void testGetUser_UserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser("nonexistent@example.com", "password123");
        });

        assertEquals("User not found with email: nonexistent@example.com", exception.getMessage());
    }


    @Test
    void testGetUser_UserNotFoundException() {
        String email = "test@example.com";
        String password = "somepassword";
        when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Database error"));

        assertThrows(ServiceException.class, () -> {
            userService.getUser(email, password);
        });
    }

    @Test
    void testGetUser_EmptyEmail() {
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            userService.getUser("", "password123");
        });

        assertEquals("Please provide an email address", exception.getMessage());
    }

    @Test
    void testGetUser_IncorrectPassword() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            userService.getUser("test@example.com", "incorrectpassword");
        });

        assertEquals("Incorrect password", exception.getMessage());
    }

    @Test
    void testGetUser_ServiceException() {
        String email = "test@example.com";
        String password = "Password";
        when(userRepository.findByEmail(email)).thenThrow(RuntimeException.class);

        assertThrows(ServiceException.class, () -> userService.getUser(email, password),
                "UserServiceException should be thrown for exception in repository");
    }

    @Test
    void testCreateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("StrongPassword123");

        User user = new User();
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);

        userService.createUser(userDTO);

        verify(userRepository).save(user);
        verify(modelMapper, times(1)).map(userDTO, User.class);
    }

    @Test
    void testCreateUser_InvalidEmailFormat() {
        UserDTO userDTO = new UserDTO("invalid_email", "StrongPassword123");
        assertThrows(ServiceException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testCreateUser_InvalidPasswordFormat() {
        UserDTO userDTO = new UserDTO("valid_email@example.com", "weakpassword");
        assertThrows(ServiceException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testUpdateUser_Success() throws UserNotFoundException {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("newPassword@1");

        User user = new User();
        user.setEmail(userDTO.getEmail());
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        userService.updateUser(userDTO);

        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).save(user);
        verify(modelMapper).map(userDTO, User.class);

        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testUpdateUser_EmailBlank() {
        UserDTO userDTO = new UserDTO("", "StrongPassword123");

        assertThrows(ServiceException.class, () -> userService.updateUser(userDTO));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        UserDTO userDTO = new UserDTO("test@example.com", "StrongPassword123");

        when(modelMapper.map(userDTO, User.class)).thenReturn(new User());

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);

        assertThrows(ServiceException.class, () -> {
            userService.updateUser(userDTO);
        });
    }

    @Test
    void testConvertEntityToDTO() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(modelMapper.map(user, UserDTO.class)).thenReturn(new UserDTO("test@example.com", "password"));

        UserDTO userDTO = userService.convertEntityToDTO(user);

        assertNotNull(userDTO);
        assertEquals("test@example.com", userDTO.getEmail());
        assertEquals("password", userDTO.getPassword());
    }

    @Test
    void testConvertDTOToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        User user = new User();
        user.setEmail(userDTO.getEmail());
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);

        User result = userService.convertDTOToEntity(userDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }
}
