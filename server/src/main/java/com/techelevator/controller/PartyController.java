package com.techelevator.controller;


import com.techelevator.dao.PartyDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CreatePartyDTO;
import com.techelevator.model.Party;
import com.techelevator.model.PlayerCharacter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/parties")
public class PartyController {

    private PartyDao partyDao;

    public PartyController(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<Party> list() {
        return partyDao.getParties();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public Party getById(@PathVariable int id) {
        Party party = partyDao.getPartyById(id);

        if (party == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Party not found.");
        }

        List<PlayerCharacter> characters = partyDao.getCharactersByPartyId(id);
        party.setCharacters(characters);

        return party;
    }

    @RequestMapping(path = "/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public Party getByName(@PathVariable String name) {
        Party party = partyDao.getPartyByName(name);
        if (party == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Party not found.");
        } else {
            return party;
        }
    }

    @RequestMapping(path = "/{partyId}/characters", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<PlayerCharacter> getCharactersByParty(@PathVariable int partyId) {
        return partyDao.getCharactersByPartyId(partyId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public Party add(@Valid @RequestBody CreatePartyDTO dto, Principal principal) {
        Party newParty = new Party();
        newParty.setPartyName(dto.getPartyName());
        Party created = partyDao.createParty(newParty);

        for (Integer characterId : dto.getCharacterIds()) {
            partyDao.addCharacterToParty(created.getPartyId(), characterId);
        }

        created.setCharacters(partyDao.getCharactersByPartyId(created.getPartyId()));
        return created;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public Party update(@Valid @RequestBody Party party, @PathVariable int id) {
        party.setPartyId(id);
        try {
            Party updatedParty = partyDao.updateParty(party);
            return updatedParty;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Party not found.");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        partyDao.deletePartyById(id);
    }

}
