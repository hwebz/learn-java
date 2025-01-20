package com.hwebz.dreamshops.repositories;

import com.hwebz.dreamshops.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(String role);
}
