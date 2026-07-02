package com.home.service.auth;

import com.home.dto.auth.UserRequest;
import com.home.dto.auth.UserResponse;
import com.home.event.auth.UserRegisteredEvent;
import com.home.model.auth.Role;
import com.home.model.auth.User;
import com.home.repository.auth.RoleRepository;
import com.home.repository.auth.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

    @Transactional
    public UserResponse register(UserRequest userRequest){
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
        UserResponse userResponse = new UserResponse();
        userResponse.setName(savedUser.getName());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setMessage("User registered successfully!");
        return userResponse;
    }
}