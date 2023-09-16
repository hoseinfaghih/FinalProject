package com.example.finalproject;

import com.example.finalproject.model.Role;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Start implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        var user = User.builder()
                .name("Agha Reza")
                .email("reza@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }
}
