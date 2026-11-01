package com.esdl.mentorshipplatform.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for creating a mentorship request from the frontend.
 */
public class CreateRequestDTO {

    @NotNull(message = "Mentee ID is required")
    private Long menteeId;

    @NotNull(message = "Mentor ID is required")
    private Long mentorId;

    private String message;

    public CreateRequestDTO() {
    }

    public CreateRequestDTO(Long menteeId, Long mentorId, String message) {
        this.menteeId = menteeId;
        this.mentorId = mentorId;
        this.message = message;
    }

    // --- Manual Getters and Setters ---

    public Long getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(Long menteeId) {
        this.menteeId = menteeId;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
