package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.UserDetailsDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.User;
import com.techelevator.model.UserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<User> list() {
        return userDao.getUsers();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public User getById(@PathVariable int id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        } else {
            return user;
        }
    }

    @RequestMapping(path = "/username/{username}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public User getByName(@PathVariable String username) {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        } else {
            return user;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public User add(@Valid @RequestBody User user, Principal principal) {
        System.out.println(principal.getName());
        return userDao.createUser(user);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public User update(@Valid @RequestBody User user, @PathVariable int id) {
        user.setId(id);
        try {
            User updatedUser = userDao.updateUser(user);
            return updatedUser;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        userDao.deleteUserById(id);
    }

}
