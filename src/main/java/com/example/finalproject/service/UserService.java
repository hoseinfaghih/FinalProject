package com.example.finalproject.service;

import com.example.finalproject.dto.LoginRequest;
import com.example.finalproject.dto.RegisterRequest;
import com.example.finalproject.dto.UserLoginResponse;
import com.example.finalproject.dto.UserRegisterResponse;
import com.example.finalproject.model.Role;
import com.example.finalproject.model.User;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.security.JwtUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    Authentication authentication;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is already taken");
        }
        User requestedUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        var savedUser = repository.save(requestedUser);
        UserRegisterResponse userRegisterResponse = UserRegisterResponse.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .message("You Registered Successfully!")
                .build();
        return ResponseEntity.ok(userRegisterResponse);
    }

    public ResponseEntity<Object> login(LoginRequest loginRequest){
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtilities.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();

        return ResponseEntity.ok(UserLoginResponse.builder()
                .token(jwt)
                .email(userDetails.getEmail())
                .message("Welcome !")
                .build()
        );
    }
    public User getByEmail(String email) {
        var user = repository.findByEmail(email);
        if (!user.isPresent()) {
            return null;
        }
        User userRes = user.get();
        return userRes;

    }
}
