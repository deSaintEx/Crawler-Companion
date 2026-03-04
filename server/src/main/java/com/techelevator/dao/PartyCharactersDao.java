package com.techelevator.dao;

import com.techelevator.model.Party;
import com.techelevator.model.PlayerCharacter;
import com.techelevator.model.User;

import java.util.List;

public interface PartyCharactersDao {

    void assignCharacterToParty(int partyId, int characterId);

    void removeCharacterFromParty(int partyId, int characterId);

    List<PlayerCharacter> getCharactersByPartyId(int partyId);

    List<Party> getPartiesByCharacterId(int characterId);

    List<Party> getPartiesByUsername(String username);

    List<Party> getPartiesByFullName(String fullName);


}
