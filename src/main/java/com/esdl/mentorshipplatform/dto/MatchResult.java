package com.esdl.mentorshipplatform.dto;

import com.esdl.mentorshipplatform.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 * MatchResult represents a mentor matched with a mentee,
 * showing the calculated score and the transparent "Why this match?" breakdown.
 */
public class MatchResult {

    private User mentor;
    private double matchPercentage;
    private List<String> commonSkills = new ArrayList<>();
    private List<String> commonInterests = new ArrayList<>();
    private String explanation;

    public MatchResult() {
    }

    public MatchResult(User mentor, double matchPercentage, List<String> commonSkills, List<String> commonInterests, String explanation) {
        this.mentor = mentor;
        this.matchPercentage = matchPercentage;
        this.commonSkills = commonSkills;
        this.commonInterests = commonInterests;
        this.explanation = explanation;
    }

    // --- Manual Getters and Setters ---

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public List<String> getCommonSkills() {
        return commonSkills;
    }

    public void setCommonSkills(List<String> commonSkills) {
        this.commonSkills = commonSkills;
    }

    public List<String> getCommonInterests() {
        return commonInterests;
    }

    public void setCommonInterests(List<String> commonInterests) {
        this.commonInterests = commonInterests;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
