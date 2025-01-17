package com.hwebz.dreamshops.data;

import com.hwebz.dreamshops.models.User;
import com.hwebz.dreamshops.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        if (userRepository.findAll().isEmpty()) {
            for (int i = 1; i <= 5; i++) {
                String defaultEmail = String.format("user%d@gmail.com", i);
                if (userRepository.existsByEmail(defaultEmail)) {
                    continue;
                }
                User user = new User();
                user.setEmail(defaultEmail);
                user.setPassword("password");
                user.setFirstName("The");
                user.setLastName("User " + i);
                userRepository.save(user);
                System.out.println("User " + i + " created");
            }
        }
    }
}
