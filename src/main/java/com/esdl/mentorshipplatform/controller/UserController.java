package com.esdl.mentorshipplatform.controller;

import com.esdl.mentorshipplatform.dto.MatchResult;
import com.esdl.mentorshipplatform.entity.User;
import com.esdl.mentorshipplatform.entity.UserRole;
import com.esdl.mentorshipplatform.service.MatchingService;
import com.esdl.mentorshipplatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User profile management and mentor matching endpoints.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MatchingService matchingService;

    public UserController(UserService userService, MatchingService matchingService) {
        this.userService = userService;
        this.matchingService = matchingService;
    }

    // Create a new user profile (Mentor or Mentee)
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all users, or filter by role (e.g., /api/users?role=MENTOR)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) UserRole role) {
        if (role != null) {
            return ResponseEntity.ok(userService.getUsersByRole(role));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get a specific user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Get ranked mentor matches for a given mentee (Feature 2 & 3)
    @GetMapping("/{id}/matches")
    public ResponseEntity<List<MatchResult>> getMatchesForMentee(@PathVariable Long id) {
        List<MatchResult> matches = matchingService.findMatchesForMentee(id);
        return ResponseEntity.ok(matches);
    }
}
