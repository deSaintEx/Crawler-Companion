package com.techelevator.model;

import java.util.Objects;

public class UserCharacter {

    private int userId;
    private int characterId;

    public UserCharacter() {}

    public UserCharacter(int userId, int characterId) {
        this.userId = userId;
        this.characterId = characterId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        UserCharacter userCharacter = (UserCharacter) o;
        return userId == userCharacter.userId &&
                characterId == userCharacter.characterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, characterId);
    }

    @Override
    public String toString() {
        return "UserCharacter{" +
                "userId=" + userId + '\'' +
                ", characterId='" + characterId + '\'' +
                '}';
    }

}
