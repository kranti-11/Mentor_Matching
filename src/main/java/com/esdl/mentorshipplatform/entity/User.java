package com.esdl.mentorshipplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * User Entity represents a person registered on the platform.
 * A User can either be a MENTOR or a MENTEE.
 */
@Entity
@Table(name = "users")
public class User {

    // Primary key with auto-increment ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name of the user
    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    // Unique email address used for contact / identification
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true)
    private String email;

    // Role: MENTOR or MENTEE
    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // Comma-separated list of technical/functional skills (e.g., "Java, Spring Boot, MySQL")
    @Column(length = 500)
    private String skills;

    // Comma-separated list of domains/interests (e.g., "Backend Development, Cloud, AI")
    @Column(length = 500)
    private String interests;

    // Total years of relevant professional or academic experience
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience = 0;

    // Short summary or introduction written by the user
    @Column(length = 1000)
    private String bio;

    // Default No-Argument Constructor (Required by JPA/Hibernate)
    public User() {
    }

    // Parameterized Constructor for easy creation of user instances
    public User(String name, String email, UserRole role, String skills, String interests, Integer yearsOfExperience, String bio) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.skills = skills;
        this.interests = interests;
        this.yearsOfExperience = yearsOfExperience != null ? yearsOfExperience : 0;
        this.bio = bio;
    }

    // --- Manual Getters and Setters (Plain Java, No Lombok) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", skills='" + skills + '\'' +
                ", interests='" + interests + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}
