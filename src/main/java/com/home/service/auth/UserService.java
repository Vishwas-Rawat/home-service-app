package com.home.service.auth;

import com.home.config.JwtTokenProvider;
import com.home.dto.auth.LoginRequest;
import com.home.dto.auth.LoginResponse;
import com.home.dto.auth.RegisterRequest;
import com.home.dto.auth.RegisterResponse;
import com.home.event.auth.UserRegisteredEvent;
import com.home.model.auth.Role;
import com.home.model.auth.User;
import com.home.repository.auth.RoleRepository;
import com.home.repository.auth.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public RegisterResponse register(RegisterRequest userRequest){

        if(userRequest.getRole().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("Admin registration is not allowed");
        }

    // 1. Check if email is already registered
    if(userRepository.existsByEmail(userRequest.getEmail())){
        throw new RuntimeException("Error: Email is already taken!");
    }

    // 2. Fetch the corresponding Role entity from database (CUSTOMER or PROVIDER)
    Role role = roleRepository.findByName(userRequest.getRole().toUpperCase())
            .orElseThrow(() -> new RuntimeException("Error: Role not found"));

    // 3. Create and populate the User entity
        User tempUser = new User();
        tempUser.setName(userRequest.getName());
        tempUser.setEmail(userRequest.getEmail());
        tempUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        tempUser.setRole(role);
        tempUser.setStatus("ACTIVE");


    // 4. Save the user to the database
       User savedUser = userRepository.save(tempUser);

    // 5. Publish the registration event to trigger profile creation in other modules
        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser));

    // 6. Build and return the response DTO
        RegisterResponse userResponse = new RegisterResponse();
        userResponse.setName(savedUser.getName());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setMessage("User registered successfully!");
        return userResponse;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        // 1. Verify the email and password credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // 2. Set authentication in context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate the token
        String jwtToken = jwtTokenProvider.generateToken(authentication);

        // 4. Return the token and type "Bearer"
        return new LoginResponse(jwtToken, "Bearer");
    }
}