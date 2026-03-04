package com.techelevator.model;

import java.util.Objects;

public class PartyCharacters {

    private int partyId;

    private int characterId;

    public PartyCharacters() {}

    public PartyCharacters(int partyId, int characterId) {
        this.partyId = partyId;
        this.characterId = characterId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyCharacters partyCharacters = (PartyCharacters) o;
        return partyId == partyCharacters.partyId &&
                characterId == partyCharacters.characterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyId, characterId);
    }

    @Override
    public String toString() {
        return "PartyCharacter{" +
                "partyId=" + partyId + '\'' +
                ", characterId='" + characterId + '\'' +
                '}';
    }

}
