package com.esdl.mentorshipplatform.repository;

import com.esdl.mentorshipplatform.entity.User;
import com.esdl.mentorshipplatform.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository provides built-in CRUD operations for the User entity.
 * Spring Data JPA generates the SQL implementation automatically at runtime.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find all users by their role (e.g., all MENTORs or all MENTEEs)
    List<User> findByRole(UserRole role);

    // Find a user by their email address (used to prevent duplicate accounts)
    Optional<User> findByEmail(String email);
}
