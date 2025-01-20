package com.hwebz.dreamshops.data;

import com.hwebz.dreamshops.models.Role;
import com.hwebz.dreamshops.models.User;
import com.hwebz.dreamshops.repositories.RoleRepository;
import com.hwebz.dreamshops.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExists(defaultRoles);

        createDefaultAdminIfNotExists();
        createDefaultUserIfNotExists();
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get(0);
//        if (userRepository.findAll().isEmpty()) {
            for (int i = 1; i <= 5; i++) {
                String defaultEmail = String.format("admin%d@gmail.com", i);
                if (userRepository.existsByEmail(defaultEmail)) {
                    continue;
                }
                User user = new User();
                user.setEmail(defaultEmail);
                user.setPassword(passwordEncoder.encode("password"));
                user.setFirstName("The");
                user.setLastName("Admin " + i);
                user.setRoles(Set.of(adminRole));
                userRepository.save(user);
                System.out.println("Admin " + i + " created");
            }
//        }
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get(0);
//        if (userRepository.findAll().isEmpty()) {
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = String.format("user%d@gmail.com", i);
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("password"));
            user.setFirstName("The");
            user.setLastName("User " + i);
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("User " + i + " created");
        }
//        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream().filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
