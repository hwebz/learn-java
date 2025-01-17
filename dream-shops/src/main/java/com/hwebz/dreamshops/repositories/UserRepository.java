package com.hwebz.dreamshops.repositories;

import com.hwebz.dreamshops.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
