package com.example.shopapp.services.interfaces;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO);
    String login(String phoneNumber, String password);
}
