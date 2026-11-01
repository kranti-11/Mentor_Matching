package com.esdl.mentorshipplatform.service;

import com.esdl.mentorshipplatform.dto.MatchResult;
import com.esdl.mentorshipplatform.entity.User;
import com.esdl.mentorshipplatform.entity.UserRole;
import com.esdl.mentorshipplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MatchingService implements the core matchmaking algorithm.
 *
 * Formula:
 * Score = (common skills / mentee skills) * 70
 * + (common interests / mentee interests) * 30
 *
 * It ranks mentors by match percentage and provides a transparent "Why this
 * match?" explanation.
 */
@Service
public class MatchingService {

    private final UserRepository userRepository;

    public MatchingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds and ranks all mentors for a given mentee.
     */
    public List<MatchResult> findMatchesForMentee(Long menteeId) {
        User mentee = userRepository.findById(menteeId)
                .orElseThrow(() -> new IllegalArgumentException("Mentee not found with ID: " + menteeId));

        List<User> mentors = userRepository.findByRole(UserRole.MENTOR);
        List<MatchResult> results = new ArrayList<>();

        Set<String> menteeSkills = parseItems(mentee.getSkills());
        Set<String> menteeInterests = parseItems(mentee.getInterests());

        for (User mentor : mentors) {
            Set<String> mentorSkills = parseItems(mentor.getSkills());
            Set<String> mentorInterests = parseItems(mentor.getInterests());

            // 1. Calculate Common Skills
            List<String> commonSkills = new ArrayList<>();
            for (String skill : menteeSkills) {
                if (mentorSkills.contains(skill.toLowerCase())) {
                    commonSkills.add(skill); // keep original casing
                }
            }

            // 2. Calculate Common Interests
            List<String> commonInterests = new ArrayList<>();
            for (String interest : menteeInterests) {
                if (mentorInterests.contains(interest.toLowerCase())) {
                    commonInterests.add(interest);
                }
            }

            // 3. Apply the Formula
            double skillScore = 0.0;
            if (!menteeSkills.isEmpty()) {
                skillScore = ((double) commonSkills.size() / menteeSkills.size()) * 70.0;
            }

            double interestScore = 0.0;
            if (!menteeInterests.isEmpty()) {
                interestScore = ((double) commonInterests.size() / menteeInterests.size()) * 30.0;
            }

            double totalScore = skillScore + interestScore;
            // Cap at 100% and round to 1 decimal place
            totalScore = Math.min(100.0, Math.round(totalScore * 10.0) / 10.0);

            // 4. Generate transparent "Why this match?" explanation
            String explanation = generateExplanation(
                    commonSkills.size(), menteeSkills.size(), skillScore,
                    commonInterests.size(), menteeInterests.size(), interestScore,
                    totalScore);

            results.add(new MatchResult(mentor, totalScore, commonSkills, commonInterests, explanation));
        }

        // Sort mentors by match score descending (highest match first).
        // If tied, prioritize mentor with more years of experience.
        results.sort((a, b) -> {
            int scoreCompare = Double.compare(b.getMatchPercentage(), a.getMatchPercentage());
            if (scoreCompare != 0) {
                return scoreCompare;
            }
            int expA = a.getMentor().getYearsOfExperience() != null ? a.getMentor().getYearsOfExperience() : 0;
            int expB = b.getMentor().getYearsOfExperience() != null ? b.getMentor().getYearsOfExperience() : 0;
            return Integer.compare(expB, expA);
        });

        return results;
    }

    /**
     * Splits comma-separated strings into a cleaned, lowercased Set for
     * case-insensitive matching.
     */
    private Set<String> parseItems(String commaSeparated) {
        if (commaSeparated == null || commaSeparated.trim().isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(commaSeparated.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    /**
     * Creates human-readable explanation of how the score was calculated.
     */
    private String generateExplanation(int commonSkills, int totalSkills, double skillScore,
            int commonInterests, int totalInterests, double interestScore,
            double totalScore) {
        return String.format(
                "Matched %d/%d skills (%.1f%% of 70) and %d/%d interests (%.1f%% of 30) = %.1f%% overall match.",
                commonSkills, totalSkills, skillScore,
                commonInterests, totalInterests, interestScore,
                totalScore);
    }
}
