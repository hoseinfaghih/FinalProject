package com.example.finalproject;

import com.example.finalproject.dto.RegisterRequest;
import com.example.finalproject.model.Role;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.security.JwtUtilities;
import com.example.finalproject.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtilities jwtUtilities;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserService userService;

    @Test
    public void registrationTest() {
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(encoder.encode(any())).thenReturn("encodedPassword");

        RegisterRequest registrationRequest = new RegisterRequest("Test", "test@gmail.com",
                "idkwhattosay", Role.ADMIN.name());

        ResponseEntity<Object> response = userService.register(registrationRequest);
        String body = (String) response.getBody();
        Assertions.assertThat(body.equals("You Registered Successfully!"));
    }


}
