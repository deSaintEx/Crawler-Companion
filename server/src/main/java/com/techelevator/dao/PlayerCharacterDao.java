package com.techelevator.dao;

import com.techelevator.model.PlayerCharacter;

import java.util.List;

public interface PlayerCharacterDao {

    List<PlayerCharacter> getPlayerCharacters();

    PlayerCharacter getPlayerCharacterById(int characterId);

    PlayerCharacter getPlayerCharacterByName(String characterName);

    PlayerCharacter createPlayerCharacter(PlayerCharacter newPlayerCharacter);

    PlayerCharacter updatePlayerCharacter(PlayerCharacter updatedPlayerCharacter);

    int deletePlayerCharacterById(int characterId);

}
