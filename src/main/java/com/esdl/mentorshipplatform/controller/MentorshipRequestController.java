package com.esdl.mentorshipplatform.controller;

import com.esdl.mentorshipplatform.dto.CreateRequestDTO;
import com.esdl.mentorshipplatform.entity.MentorshipRequest;
import com.esdl.mentorshipplatform.service.MentorshipRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing mentorship requests (send, view, accept, reject).
 */
@RestController
@RequestMapping("/api/requests")
public class MentorshipRequestController {

    private final MentorshipRequestService requestService;

    public MentorshipRequestController(MentorshipRequestService requestService) {
        this.requestService = requestService;
    }

    // Send a new mentorship request
    @PostMapping
    public ResponseEntity<MentorshipRequest> sendRequest(@Valid @RequestBody CreateRequestDTO dto) {
        MentorshipRequest created = requestService.sendRequest(
                dto.getMenteeId(),
                dto.getMentorId(),
                dto.getMessage()
        );
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // View requests received by a mentor
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<MentorshipRequest>> getRequestsForMentor(@PathVariable Long mentorId) {
        return ResponseEntity.ok(requestService.getRequestsForMentor(mentorId));
    }

    // View requests sent by a mentee
    @GetMapping("/mentee/{menteeId}")
    public ResponseEntity<List<MentorshipRequest>> getRequestsForMentee(@PathVariable Long menteeId) {
        return ResponseEntity.ok(requestService.getRequestsForMentee(menteeId));
    }

    // Accept a mentorship request
    @PutMapping("/{id}/accept")
    public ResponseEntity<MentorshipRequest> acceptRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.acceptRequest(id));
    }

    // Reject a mentorship request
    @PutMapping("/{id}/reject")
    public ResponseEntity<MentorshipRequest> rejectRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.rejectRequest(id));
    }
}
