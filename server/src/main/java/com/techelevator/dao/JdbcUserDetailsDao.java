package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import com.techelevator.model.Plot;
import com.techelevator.model.UserDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDetailsDao implements UserDetailsDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDetailsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserDetails> getDetailsForAllUsers() {

        List<UserDetails> userDetails = new ArrayList<>();
        String sql = "SELECT full_name, user_id, in_a_party, in_a_campaign, has_character " +
                "FROM user_details " +
                "ORDER BY user_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                userDetails.add(mapRowToUserDetails(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return userDetails;
    }

    @Override
    public UserDetails getUserDetailsById(int userId) {

        UserDetails userDetails = null;
        String sql = "SELECT full_name, user_id, in_a_party, in_a_campaign, has_character " +
                "FROM user_details " +
                "WHERE user_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                userDetails = mapRowToUserDetails(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return userDetails;
    }

    @Override
    public UserDetails getUserDetailsByFullName(String fullName) {

        UserDetails userDetails = null;
        String sql = "SELECT full_name, user_id, in_a_party, in_a_campaign, has_character " +
                "FROM user_details " +
                "WHERE full_name ILIKE ?";

        try {
            String nameToSearch = "%" + fullName + "%";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, nameToSearch);
            if (results.next()) {
                userDetails = mapRowToUserDetails(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return userDetails;
    }

    @Override
    public UserDetails createUserDetails(UserDetails newUserDetails) {

        String sql = "INSERT INTO user_details (full_name, user_id, in_a_party, " +
                "in_a_campaign, has_character) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, newUserDetails.getFullName(), newUserDetails.getUserId(),
                    newUserDetails.isInAParty(), newUserDetails.isInACampaign(), newUserDetails.getHasCharacter());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return getUserDetailsById(newUserDetails.getUserId());
    }

    @Override
    public UserDetails updateUserDetails(UserDetails updatedUserDetails) {

        UserDetails userDetails = null;
        String sql = "UPDATE user_details " +
                "SET full_name = ?, in_a_party = ?, in_a_campaign =?, has_character = ? " +
                "WHERE user_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedUserDetails.getFullName(),
                    updatedUserDetails.isInAParty(), updatedUserDetails.isInACampaign(),
                    updatedUserDetails.getHasCharacter(), updatedUserDetails.getUserId());

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least 1");
            } else {
                userDetails = getUserDetailsById(updatedUserDetails.getUserId());
            }
        } catch (CannotGetJdbcConnectionException e ) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return userDetails;
    }

    @Override
    public int deleteUserDetailsById(int userId) {

        String sql = "DELETE FROM user_details WHERE user_id = ?";
        try {
            return jdbcTemplate.update(sql, userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private UserDetails mapRowToUserDetails(SqlRowSet rs) {
        UserDetails userDetails = new UserDetails();
        userDetails.setFullName(rs.getString("full_name"));
        userDetails.setUserId(rs.getInt("user_id"));
        userDetails.setInAParty(rs.getBoolean("in_a_party"));
        userDetails.setInACampaign(rs.getBoolean("in_a_campaign"));
        userDetails.setHasCharacter(rs.getBoolean("has_character"));
        return userDetails;
    }

}
