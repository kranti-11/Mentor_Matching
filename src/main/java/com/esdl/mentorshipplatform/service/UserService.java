package com.esdl.mentorshipplatform.service;

import com.esdl.mentorshipplatform.entity.User;
import com.esdl.mentorshipplatform.entity.UserRole;
import com.esdl.mentorshipplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService handles business logic for user management (create, view, list).
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a new user profile
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A user with email " + user.getEmail() + " already exists.");
        }
        return userRepository.save(user);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve users filtered by role (MENTOR or MENTEE)
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    // Retrieve a single user by primary key ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }
}
