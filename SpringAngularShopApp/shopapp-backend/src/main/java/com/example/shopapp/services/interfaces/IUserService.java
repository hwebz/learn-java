package com.example.shopapp.services.interfaces;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId)  throws DataNotFoundException;
    User getUserDetailsFromToken(String token) throws Exception;
    User updateUser(UserDTO updatedUserDTO, Long userId) throws Exception;
}
