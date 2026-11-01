package com.esdl.mentorshipplatform.entity;

/**
 * RequestStatus represents the lifecycle of a mentorship request:
 * - PENDING: Request has been sent by mentee and is awaiting mentor response
 * - ACCEPTED: Mentor has accepted the mentorship request
 * - REJECTED: Mentor has declined the mentorship request
 */
public enum RequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}
