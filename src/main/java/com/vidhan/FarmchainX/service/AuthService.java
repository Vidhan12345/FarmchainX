package com.vidhan.FarmchainX.service;

import com.vidhan.FarmchainX.dto.LoginRequest;
import com.vidhan.FarmchainX.dto.LoginResponse;
import com.vidhan.FarmchainX.dto.SignupRequest;
import com.vidhan.FarmchainX.entity.User;
import com.vidhan.FarmchainX.entity.UserRole;
import com.vidhan.FarmchainX.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.vidhan.FarmchainX.config.JwtUtils jwtUtils;

    /**
     * Register new user
     */
    public LoginResponse registerUser(SignupRequest signupRequest) {
        // Check if email already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());

        // Hash password using BCrypt
        user.setPassword(BCrypt.hashpw(signupRequest.getPassword(), BCrypt.gensalt(12)));

        user.setPhone(signupRequest.getPhone());
        user.setAddress(signupRequest.getAddress());
        user.setCompany(signupRequest.getCompany());

        // Parse and set role
        try {
            UserRole userRole = UserRole.valueOf(signupRequest.getRole().toUpperCase());
            user.setRole(userRole);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error: Invalid role: " + signupRequest.getRole());
        }

        user.setVerified(false); // New users are not verified by default

        User savedUser = userRepository.save(user);

        // Generate JWT token
        String jwtToken = jwtUtils.generateTokenFromUsername(savedUser.getEmail());

        // Return login response with user info
        return createLoginResponse(savedUser, jwtToken);
    }

    /**
     * Login user
     */
    public LoginResponse loginUser(LoginRequest loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: Invalid email or password"));

        // Verify password
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Error: Invalid email or password");
        }

        // Generate JWT token
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getEmail());

        return createLoginResponse(user, jwtToken);
    }

    /**
     * Helper method to create LoginResponse
     */
    private LoginResponse createLoginResponse(User user, String token) {
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getProfileImage(),
                user.getPhone(),
                user.getAddress(),
                user.getCompany(),
                user.getVerified());

        return new LoginResponse(token, userInfo);
    }
}
