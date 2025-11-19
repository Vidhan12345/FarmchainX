package com.vidhan.FarmchainX.service;

import com.vidhan.FarmchainX.dto.LoginRequest;
import com.vidhan.FarmchainX.dto.LoginResponse;
import com.vidhan.FarmchainX.dto.SignupRequest;
import com.vidhan.FarmchainX.entity.ERole;
import com.vidhan.FarmchainX.entity.Role;
import com.vidhan.FarmchainX.entity.User;
import com.vidhan.FarmchainX.repository.RoleRepository;
import com.vidhan.FarmchainX.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Register new user
     */
    public String registerUser(SignupRequest signupRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());

        // Hash password using BCrypt
        user.setPassword(BCrypt.hashpw(signupRequest.getPassword(), BCrypt.gensalt(12)));

        user.setPhone(signupRequest.getPhone());
        user.setAddress(signupRequest.getAddress());
        user.setCity(signupRequest.getCity());
        user.setState(signupRequest.getState());
        user.setPincode(signupRequest.getPincode());

        // Assign roles
        Set<Role> roles = new HashSet<>();
        signupRequest.getRoles().forEach(roleName -> {
            try {
                ERole eRole = ERole.valueOf("ROLE_" + roleName.toUpperCase());
                Role role = roleRepository.findByRoleName(eRole)
                        .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " not found"));
                roles.add(role);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Error: Invalid role name: " + roleName);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully!";
    }

    /**
     * Login user
     */
    public LoginResponse loginUser(LoginRequest loginRequest) {
        // Find user by username
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: Invalid username or password"));

        // Check if user is active
        if (!user.getIsActive()) {
            throw new RuntimeException("Error: User account is inactive");
        }

        // Verify password
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Error: Invalid username or password");
        }

        // Get roles
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList());

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles,
                "Login successful!"
        );
    }
}
