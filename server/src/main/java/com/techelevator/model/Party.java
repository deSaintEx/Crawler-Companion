package com.techelevator.model;

import java.util.List;
import java.util.Objects;

public class Party {

    private int partyId;
    private String partyName;
    private List<PlayerCharacter> characters;

    public Party() { }

    public Party(int partyId, String partyName) {
        this.partyId = partyId;
        this.partyName = partyName;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public List<PlayerCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<PlayerCharacter> characters) {
        this.characters = characters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Party party = (Party) o;
        return partyId == party.partyId &&
                Objects.equals(partyName, party.partyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyId, partyName);
    }

    @Override
    public String toString() {
        return "Party{" +
                "partyId='" + partyId + '\'' +
                ", partyName='" + partyName + '\'' +
                ", characters='" + characters + '\'' +
                '}';
    }

}
