package com.techelevator.controller;

import com.techelevator.dao.UserDetailsDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Party;
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
@RequestMapping("/user-details")
//@PreAuthorize("isAuthenticated")
public class UserDetailsController {

    private UserDetailsDao userDetailsDao;

    public UserDetailsController(UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<UserDetails> list() {
        return userDetailsDao.getDetailsForAllUsers();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public UserDetails getById(@PathVariable int id) {
        UserDetails userDetails = userDetailsDao.getUserDetailsById(id);
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Details not found.");
        } else {
            return userDetails;
        }
    }

    @RequestMapping(path = "/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public UserDetails getByName(@PathVariable String name) {
        UserDetails userDetails = userDetailsDao.getUserDetailsByFullName(name);
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Details not found.");
        } else {
            return userDetails;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDetails add(@Valid @RequestBody UserDetails userDetails, Principal principal) {
        System.out.println(principal.getName());
        return userDetailsDao.createUserDetails(userDetails);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDetails update(@Valid @RequestBody UserDetails userDetails, @PathVariable int id) {
        userDetails.setUserId(id);
        try {
            UserDetails updatedUserDetails = userDetailsDao.updateUserDetails(userDetails);
            return updatedUserDetails;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Details not found.");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        userDetailsDao.deleteUserDetailsById(id);
    }

}
