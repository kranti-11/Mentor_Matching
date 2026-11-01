package com.esdl.mentorshipplatform.service;

import com.esdl.mentorshipplatform.entity.MentorshipRequest;
import com.esdl.mentorshipplatform.entity.RequestStatus;
import com.esdl.mentorshipplatform.entity.User;
import com.esdl.mentorshipplatform.entity.UserRole;
import com.esdl.mentorshipplatform.repository.MentorshipRequestRepository;
import com.esdl.mentorshipplatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataInitializer runs automatically on application startup.
 * It seeds 5 Mentors and 5 Mentees, plus sample mentorship requests,
 * so the application is ready for demonstration immediately.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MentorshipRequestRepository requestRepository;

    public DataInitializer(UserRepository userRepository, MentorshipRequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return; // Already initialized
        }

        // ========================================
        // 1. Seed 5 Mentors
        // ========================================
        User mentor1 = new User(
                "Dr. Rajesh Kumar",
                "rajesh.kumar@mentor.com",
                UserRole.MENTOR,
                "Java, Spring Boot, Microservices, Hibernate, REST API, MySQL",
                "Backend Development, Distributed Systems, Cloud Architecture",
                8,
                "Senior Tech Lead with 8+ years experience in enterprise Java and microservices architecture."
        );

        User mentor2 = new User(
                "Priya Sharma",
                "priya.sharma@mentor.com",
                UserRole.MENTOR,
                "Python, Machine Learning, Data Science, TensorFlow, SQL, Pandas",
                "Artificial Intelligence, Deep Learning, Predictive Modeling",
                5,
                "Data Scientist passionate about helping students break into AI, NLP, and machine learning."
        );

        User mentor3 = new User(
                "Amit Patel",
                "amit.patel@mentor.com",
                UserRole.MENTOR,
                "JavaScript, React, Node.js, HTML, CSS, MongoDB, TypeScript",
                "Frontend Development, Full Stack, UI/UX Design",
                6,
                "Full-stack engineer specializing in modern web frameworks and reactive user interfaces."
        );

        User mentor4 = new User(
                "Sneha Roy",
                "sneha.roy@mentor.com",
                UserRole.MENTOR,
                "Java, Python, Docker, Kubernetes, AWS, CI/CD, Linux",
                "DevOps, Cloud Computing, Backend Development",
                7,
                "Cloud & DevOps Architect helping students understand containerization and automated deployments."
        );

        User mentor5 = new User(
                "Vikram Malhotra",
                "vikram.malhotra@mentor.com",
                UserRole.MENTOR,
                "C++, Data Structures, Algorithms, System Design, SQL",
                "Competitive Programming, Core Engineering, High Performance Systems",
                9,
                "Staff Software Engineer passionate about DSA, competitive coding, and placement preparation."
        );

        userRepository.save(mentor1);
        userRepository.save(mentor2);
        userRepository.save(mentor3);
        userRepository.save(mentor4);
        userRepository.save(mentor5);

        // ========================================
        // 2. Seed 5 Mentees
        // ========================================
        User mentee1 = new User(
                "Aarav Mehta",
                "aarav.mehta@student.com",
                UserRole.MENTEE,
                "Java, Spring Boot, MySQL",
                "Backend Development, Cloud Architecture",
                0,
                "3rd-year B.Tech IT student eager to master Spring Boot and backend engineering."
        );

        User mentee2 = new User(
                "Ananya Sen",
                "ananya.sen@student.com",
                UserRole.MENTEE,
                "Python, Machine Learning, SQL",
                "Artificial Intelligence, Deep Learning",
                0,
                "Final-year student seeking industry guidance for an AI/ML capstone project."
        );

        User mentee3 = new User(
                "Rohan Verma",
                "rohan.verma@student.com",
                UserRole.MENTEE,
                "JavaScript, React, HTML, CSS",
                "Frontend Development, UI/UX Design",
                1,
                "Frontend enthusiast looking for mentorship on React architecture and responsive web apps."
        );

        User mentee4 = new User(
                "Neha Joshi",
                "neha.joshi@student.com",
                UserRole.MENTEE,
                "Java, Docker, AWS",
                "DevOps, Cloud Computing",
                0,
                "Aspiring cloud engineer wanting practical advice on Docker, Kubernetes, and AWS."
        );

        User mentee5 = new User(
                "Kabir Das",
                "kabir.das@student.com",
                UserRole.MENTEE,
                "Data Structures, C++, Java",
                "Competitive Programming, System Design",
                0,
                "Pre-final year student preparing for product-company technical coding rounds."
        );

        userRepository.save(mentee1);
        userRepository.save(mentee2);
        userRepository.save(mentee3);
        userRepository.save(mentee4);
        userRepository.save(mentee5);

        // ========================================
        // 3. Seed Sample Mentorship Requests
        // ========================================
        MentorshipRequest req1 = new MentorshipRequest(
                mentee1,
                mentor1,
                "Hello Dr. Rajesh! I saw your profile and would love your guidance on Spring Boot microservices and my final year project."
        );
        req1.setStatus(RequestStatus.PENDING);

        MentorshipRequest req2 = new MentorshipRequest(
                mentee2,
                mentor2,
                "Hi Priya! I'm building an NLP project for college and would really appreciate your mentorship on ML pipelines."
        );
        req2.setStatus(RequestStatus.ACCEPTED);

        requestRepository.save(req1);
        requestRepository.save(req2);

        System.out.println(">>> SEED DATA LOADED: 5 Mentors, 5 Mentees, and 2 Requests ready in H2 database!");
    }
}
