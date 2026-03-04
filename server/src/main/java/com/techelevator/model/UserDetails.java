package com.techelevator.model;

import java.util.Objects;

public class UserDetails {

    private String fullName;
    private int userId;
    private boolean inAParty;
    private boolean inACampaign;
    private boolean hasCharacter;

    public UserDetails() { }

    public UserDetails(String fullName, int userId, boolean inAParty, boolean inACampaign, boolean hasCharacter) {
        this.fullName = fullName;
        this.userId = userId;
        this.inAParty = inAParty;
        this.inACampaign = inACampaign;
        this.hasCharacter = hasCharacter;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isInAParty() {
        return inAParty;
    }

    public void setInAParty(boolean inAParty) {
        this.inAParty = inAParty;
    }

    public boolean isInACampaign() {
        return inACampaign;
    }

    public void setInACampaign(boolean inACampaign) {
        this.inACampaign = inACampaign;
    }

    public boolean getHasCharacter() {
        return hasCharacter;
    }

    public void setHasCharacter(boolean hasCharacter) {
        this.hasCharacter = hasCharacter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails details = (UserDetails) o;
        return fullName == details.fullName &&
                userId == details.userId &&
                inAParty == details.inAParty &&
                inACampaign == details.inACampaign &&
                hasCharacter == details.hasCharacter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, userId, inAParty, inACampaign, hasCharacter);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "fullName=" + fullName +
                ", userId='" + userId + '\'' +
                ", inAParty=" + inAParty + '\'' +
                ", inACampaign=" + inACampaign + '\'' +
                ", hasCharacter=" + hasCharacter + '\'' +
                '}';
    }

}
