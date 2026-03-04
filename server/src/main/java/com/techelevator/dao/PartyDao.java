package com.techelevator.dao;

import com.techelevator.model.Party;
import com.techelevator.model.PlayerCharacter;

import java.util.List;

public interface PartyDao {

    List<Party> getParties();

    Party getPartyById(int partyId);

    Party getPartyByName(String partyName);

    List<PlayerCharacter> getCharactersByPartyId(int partyId);

    Party createParty(Party newParty);

    Party updateParty(Party updatedParty);

    void addCharacterToParty(int partyId, int characterId);

    int deletePartyById(int partyId);

}
