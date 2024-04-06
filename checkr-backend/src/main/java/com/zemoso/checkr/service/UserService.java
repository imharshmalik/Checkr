package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.UserDTO;
import com.zemoso.checkr.entities.User;
import com.zemoso.checkr.exception.UserNotFoundException;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO convertEntityToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertDTOToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO getUser(String email, String password) throws UserNotFoundException{
        try {
            if(email.isBlank()) {
                throw new ServiceException("Please provide an email address");
            }

            User user = userRepository.findByEmail(email);

            if (user == null) {
                throw new UserNotFoundException("User not found with email: " + email);
            }

            if (!user.getPassword().equals(password)) {
                throw new ServiceException("Incorrect password");
            }

            return convertEntityToDTO(user);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public UserDTO createUser(UserDTO userDTO) {
        try {
            if (!userDTO.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                throw new ServiceException("Please enter a valid email");
            }

            if (!userDTO.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\S{6,}$")) {
                throw new ServiceException("Please enter a valid password");
            }

            User newUser = convertDTOToEntity(userDTO);
            userRepository.save(newUser);
            return convertEntityToDTO(newUser);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public UserDTO updateUser(UserDTO userDTO){
        try {
        if(userDTO.getEmail().isBlank()) {
            throw new ServiceException("Please provide an email address");
        }

        User user = convertDTOToEntity(userDTO);

        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            throw new ServiceException("User not found with email: " + user.getEmail());
        }

        existingUser.setPassword(user.getPassword());
        User updatedUser = userRepository.save(existingUser);
        return convertEntityToDTO(updatedUser);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
