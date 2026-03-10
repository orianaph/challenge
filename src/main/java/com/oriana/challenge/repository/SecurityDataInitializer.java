package com.oriana.challenge.repository;

import com.oriana.challenge.entity.User;
import com.oriana.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class SecurityDataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(SecurityDataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initializeUsers() {
        return args -> {
            try {
                logger.info("Initializing security users...");

                if (userRepository.findByUsername("admin").isEmpty()) {
                    User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN");
                    userRepository.save(admin);
                    logger.info("Created admin user: admin/admin123");
                }

                if (userRepository.findByUsername("user").isEmpty()) {
                    User user = new User("user", passwordEncoder.encode("user123"), "USER");
                    userRepository.save(user);
                    logger.info("Created user: user/user123");
                }

                logger.info("Security users initialization completed");
            } catch (Exception e) {
                logger.error("Error initializing security users", e);
            }
        };
    }
}