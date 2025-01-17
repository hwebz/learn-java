package com.hwebz.dreamshops.services.user;

import com.hwebz.dreamshops.dtos.UserDTO;
import com.hwebz.dreamshops.exception.AlreadyExistsException;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.User;
import com.hwebz.dreamshops.repositories.UserRepository;
import com.hwebz.dreamshops.requests.CreateUserRequest;
import com.hwebz.dreamshops.requests.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId).map(this::convertToDTO).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDTO createUser(CreateUserRequest requestUser) {
        return Optional.of(requestUser)
                .filter(user -> !userRepository.existsByEmail(requestUser.getEmail()))
                .map(mu -> {
                    User user = new User();
                    user.setFirstName(mu.getFirstName());
                    user.setLastName(mu.getLastName());
                    user.setEmail(mu.getEmail());
                    user.setPassword(mu.getPassword());
                    return userRepository.save(user);
                }).map(this::convertToDTO)
                .orElseThrow(() -> new AlreadyExistsException("User is already exists"));
    }

    @Override
    public UserDTO updateUser(UserUpdateRequest user, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            return userRepository.save(existingUser);
        }).map(this::convertToDTO)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });
    }

    @Override
    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
