package com.techelevator.dao;

import com.techelevator.model.UserDetails;

import java.util.List;

public interface UserDetailsDao {

    List<UserDetails> getDetailsForAllUsers();

    UserDetails getUserDetailsById(int userId);

    UserDetails getUserDetailsByFullName(String fullName);

    UserDetails createUserDetails(UserDetails newUserDetails);

    UserDetails updateUserDetails(UserDetails updatedUserDetails);

    int deleteUserDetailsById(int userId);

}
