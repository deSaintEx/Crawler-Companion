package com.techelevator.controller;

import com.techelevator.dao.PlayerCharacterDao;
import com.techelevator.exception.DaoException;
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
@RequestMapping("/characters")
@PreAuthorize("isAuthenticated()")
public class PlayerCharacterController {

    private PlayerCharacterDao playerCharacterDao;

    public PlayerCharacterController(PlayerCharacterDao playerCharacterDao) {
        this.playerCharacterDao = playerCharacterDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<PlayerCharacter> list() {
        return playerCharacterDao.getPlayerCharacters();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public PlayerCharacter getById(@PathVariable int id) {
        PlayerCharacter playerCharacter = playerCharacterDao.getPlayerCharacterById(id);
        if (playerCharacter == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player Character not found.");
        } else {
            return playerCharacter;
        }
    }

    @RequestMapping(path = "/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public PlayerCharacter getByName(@PathVariable String name) {
        PlayerCharacter playerCharacter = playerCharacterDao.getPlayerCharacterByName(name);
        if (playerCharacter == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player Character not found.");
        } else {
            return playerCharacter;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public PlayerCharacter add(@Valid @RequestBody PlayerCharacter playerCharacter, Principal principal) {
        System.out.println(principal.getName());
        return playerCharacterDao.createPlayerCharacter(playerCharacter);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public PlayerCharacter update(@Valid @RequestBody PlayerCharacter playerCharacter, @PathVariable int id) {
        playerCharacter.setCharacterId(id);
        try {
            PlayerCharacter updatedPlayerCharacter = playerCharacterDao.updatePlayerCharacter(playerCharacter);
            return updatedPlayerCharacter;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player Character not found.");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        playerCharacterDao.deletePlayerCharacterById(id);
    }

}

// A future version will feature an ownership check so that authenticated users can also edit the characters they created
