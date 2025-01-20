package com.hwebz.dreamshops.services.user;

import com.hwebz.dreamshops.dtos.OrderDTO;
import com.hwebz.dreamshops.dtos.UserDTO;
import com.hwebz.dreamshops.models.Order;
import com.hwebz.dreamshops.models.User;
import com.hwebz.dreamshops.requests.CreateUserRequest;
import com.hwebz.dreamshops.requests.UserUpdateRequest;

public interface IUserService {
    UserDTO getUserById(Long userId);
    UserDTO createUser(CreateUserRequest user);
    UserDTO updateUser(UserUpdateRequest user, Long userId);
    void deleteUser(Long userId);
    UserDTO convertToDTO(User user);

    UserDTO getAuthenticatedUser();
}
