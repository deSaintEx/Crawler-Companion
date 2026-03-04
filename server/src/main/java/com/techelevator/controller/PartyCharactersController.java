package com.techelevator.controller;

import com.techelevator.dao.PartyCharactersDao;
import com.techelevator.model.Party;
import com.techelevator.model.PlayerCharacter;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/party-characters")
public class PartyCharactersController {

    private final PartyCharactersDao partyCharactersDao;

    public PartyCharactersController(PartyCharactersDao partyCharactersDao) {
        this.partyCharactersDao = partyCharactersDao;
    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public void assignCharacter(@RequestParam int partyId, @RequestParam int characterId) {
        partyCharactersDao.assignCharacterToParty(partyId, characterId);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeCharacter(@RequestParam int partyId, @RequestParam int characterId) {
        partyCharactersDao.removeCharacterFromParty(partyId, characterId);
    }

    @GetMapping("/party/{id}")
    @PreAuthorize("permitAll()")
    public List<PlayerCharacter> getCharacters(@PathVariable int id) {
        return partyCharactersDao.getCharactersByPartyId(id);
    }

    @GetMapping("/character/{id}")
    @PreAuthorize("permitAll()")
    public List<Party> getParties(@PathVariable int id) {
        return partyCharactersDao.getPartiesByCharacterId(id);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("permitAll()")
    public List<Party> getPartiesByUsername(@PathVariable String username) {
        return partyCharactersDao.getPartiesByUsername(username);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("permitAll()")
    public List<Party> getPartiesByFullName(@PathVariable String name) {
        return partyCharactersDao.getPartiesByFullName(name);
    }

}

// A future version will allow unauthenticated users to view a user/character/party/campaign summary featuring the relevant names from each entity