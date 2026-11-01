package com.esdl.mentorshipplatform.repository;

import com.esdl.mentorshipplatform.entity.MentorshipRequest;
import com.esdl.mentorshipplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MentorshipRequestRepository handles database operations for mentorship requests.
 */
@Repository
public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {

    // Retrieve all mentorship requests received by a specific mentor
    List<MentorshipRequest> findByMentor(User mentor);

    // Retrieve all mentorship requests sent by a specific mentee
    List<MentorshipRequest> findByMentee(User mentee);

    // Check if a request already exists between a specific mentee and mentor
    Optional<MentorshipRequest> findByMenteeAndMentor(User mentee, User mentor);
}
