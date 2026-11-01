package com.esdl.mentorshipplatform.service;

import com.esdl.mentorshipplatform.entity.MentorshipRequest;
import com.esdl.mentorshipplatform.entity.RequestStatus;
import com.esdl.mentorshipplatform.entity.User;
import com.esdl.mentorshipplatform.entity.UserRole;
import com.esdl.mentorshipplatform.repository.MentorshipRequestRepository;
import com.esdl.mentorshipplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MentorshipRequestService handles sending, viewing, accepting, and rejecting mentorship requests.
 */
@Service
public class MentorshipRequestService {

    private final MentorshipRequestRepository requestRepository;
    private final UserRepository userRepository;

    public MentorshipRequestService(MentorshipRequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    /**
     * Send a mentorship request from a Mentee to a Mentor.
     */
    public MentorshipRequest sendRequest(Long menteeId, Long mentorId, String message) {
        User mentee = userRepository.findById(menteeId)
                .orElseThrow(() -> new IllegalArgumentException("Mentee not found with ID: " + menteeId));
        User mentor = userRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Mentor not found with ID: " + mentorId));

        if (mentee.getRole() != UserRole.MENTEE) {
            throw new IllegalArgumentException("Only users with role MENTEE can send mentorship requests.");
        }
        if (mentor.getRole() != UserRole.MENTOR) {
            throw new IllegalArgumentException("Mentorship requests can only be sent to users with role MENTOR.");
        }
        if (mentee.getId().equals(mentor.getId())) {
            throw new IllegalArgumentException("You cannot send a mentorship request to yourself.");
        }

        // Prevent duplicate requests between the same pair
        requestRepository.findByMenteeAndMentor(mentee, mentor).ifPresent(existing -> {
            throw new IllegalArgumentException("A request to this mentor has already been submitted (Status: " + existing.getStatus() + ").");
        });

        MentorshipRequest request = new MentorshipRequest(mentee, mentor, message);
        return requestRepository.save(request);
    }

    /**
     * Retrieve requests received by a mentor.
     */
    public List<MentorshipRequest> getRequestsForMentor(Long mentorId) {
        User mentor = userRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Mentor not found with ID: " + mentorId));
        return requestRepository.findByMentor(mentor);
    }

    /**
     * Retrieve requests sent by a mentee.
     */
    public List<MentorshipRequest> getRequestsForMentee(Long menteeId) {
        User mentee = userRepository.findById(menteeId)
                .orElseThrow(() -> new IllegalArgumentException("Mentee not found with ID: " + menteeId));
        return requestRepository.findByMentee(mentee);
    }

    /**
     * Mentor accepts a request.
     */
    public MentorshipRequest acceptRequest(Long requestId) {
        MentorshipRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));
        request.setStatus(RequestStatus.ACCEPTED);
        return requestRepository.save(request);
    }

    /**
     * Mentor rejects a request.
     */
    public MentorshipRequest rejectRequest(Long requestId) {
        MentorshipRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));
        request.setStatus(RequestStatus.REJECTED);
        return requestRepository.save(request);
    }
}
