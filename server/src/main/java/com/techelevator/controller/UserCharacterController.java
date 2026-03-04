package com.techelevator.controller;

import com.techelevator.dao.UserCharacterDao;
import com.techelevator.model.PlayerCharacter;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user-characters")
public class UserCharacterController {

    private final UserCharacterDao userCharacterDao;

    public UserCharacterController(UserCharacterDao userCharacterDao) {
        this.userCharacterDao = userCharacterDao;
    }

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public void assignCharacter(@RequestParam int userId, @RequestParam int characterId) {
        userCharacterDao.assignCharacterToUser(userId, characterId);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeCharacter(@RequestParam int userId, @RequestParam int characterId) {
        userCharacterDao.removeCharacterFromUser(userId, characterId);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("permitAll()")
    public List<PlayerCharacter> getCharacters(@PathVariable int userId) {
        return userCharacterDao.getCharactersByUserId(userId);
    }

    @GetMapping("/character/{id}")
    @PreAuthorize("permitAll()")
    public List<User> getUsers(@PathVariable int characterId) {
        return userCharacterDao.getUsersByCharacterId(characterId);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("permitAll()")
    public List<PlayerCharacter> getCharactersByUsername(@PathVariable String username) {
        return userCharacterDao.getCharactersByUsername(username);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("permitAll()")
    public List<PlayerCharacter> getCharactersByFullName(@PathVariable String name) {
        return userCharacterDao.getCharactersByFullName(name);
    }

}

// A future version will allow unauthenticated users to view a user/character/party/campaign summary featuring the relevant names from each entity
