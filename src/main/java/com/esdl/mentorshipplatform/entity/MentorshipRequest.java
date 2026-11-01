package com.esdl.mentorshipplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * MentorshipRequest Entity represents a connection proposal sent by a Mentee
 * to a chosen Mentor.
 *
 * Relationships:
 * - Many MentorshipRequests can be sent by ONE Mentee (Many-to-One).
 * - Many MentorshipRequests can be received by ONE Mentor (Many-to-One).
 */
@Entity
@Table(name = "mentorship_requests")
public class MentorshipRequest {

    // Primary key with auto-increment ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The Mentee who initiates the mentorship request
    @NotNull(message = "Mentee is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "mentee_id", nullable = false)
    private User mentee;

    // The Mentor who receives the mentorship request
    @NotNull(message = "Mentor is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    // Status: PENDING by default, updated to ACCEPTED or REJECTED by the mentor
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    // Optional intro note from the mentee (e.g., "Looking for guidance in Java & backend engineering")
    @Column(length = 1000)
    private String message;

    // Timestamp when the request was created
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Default No-Argument Constructor (Required by JPA/Hibernate)
    public MentorshipRequest() {
        this.status = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Parameterized Constructor
    public MentorshipRequest(User mentee, User mentor, String message) {
        this.mentee = mentee;
        this.mentor = mentor;
        this.message = message;
        this.status = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // --- Manual Getters and Setters (Plain Java, No Lombok) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMentee() {
        return mentee;
    }

    public void setMentee(User mentee) {
        this.mentee = mentee;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MentorshipRequest{" +
                "id=" + id +
                ", mentee=" + (mentee != null ? mentee.getName() : null) +
                ", mentor=" + (mentor != null ? mentor.getName() : null) +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
