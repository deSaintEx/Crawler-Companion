package com.techelevator.dao;

import com.techelevator.model.PlayerCharacter;
import com.techelevator.model.User;

import java.util.List;

public interface UserCharacterDao {

    void assignCharacterToUser(int userId, int characterId);

    void removeCharacterFromUser(int userId, int characterId);

    List<PlayerCharacter> getCharactersByUserId(int userId);

    List<User> getUsersByCharacterId(int characterId);

    List<PlayerCharacter> getCharactersByUsername(String username);

    List<PlayerCharacter> getCharactersByFullName(String fullName);

}
