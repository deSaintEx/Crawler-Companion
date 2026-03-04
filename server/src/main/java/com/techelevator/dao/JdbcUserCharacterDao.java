package com.techelevator.dao;

import com.techelevator.model.PlayerCharacter;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserCharacterDao implements UserCharacterDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserCharacterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void assignCharacterToUser(int userId, int characterId) {
        String sql = "INSERT INTO user_character (user_id, character_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, characterId);
    }

    @Override
    public void removeCharacterFromUser(int userId, int characterId) {
        String sql = "DELETE FROM user_character WHERE user_id = ? AND character_id = ?";
        jdbcTemplate.update(sql, userId, characterId);
    }

    @Override
    public List<PlayerCharacter> getCharactersByUserId(int userId) {
        String sql = "SELECT pc.* FROM player_character pc " +
                "JOIN user_character uc ON pc.character_id = uc.character_id " +
                "WHERE uc.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        List<PlayerCharacter> characters = new ArrayList<>();
        while (results.next()) {
            characters.add(mapRowToPlayerCharacter(results));
        }
        return characters;
    }

    @Override
    public List<User> getUsersByCharacterId(int characterId) {
        String sql = "SELECT u.* FROM users u " +
                "JOIN user_character uc ON u.user_id = uc.user_id " +
                "WHERE uc.character_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, characterId);
        List<User> users = new ArrayList<>();
        while (results.next()) {
            users.add(mapRowToUser(results));
        }
        return users;
    }

    @Override
    public List<PlayerCharacter> getCharactersByUsername(String username) {
        String sql = "SELECT pc.* FROM player_character pc " +
                "JOIN user_character uc ON pc.character_id = uc.character_id " +
                "JOIN users u ON uc.user_id = u.user_id " +
                "WHERE u.username ILIKE ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + username + "%");
        List<PlayerCharacter> characters = new ArrayList<>();
        while (results.next()) {
            characters.add(mapRowToPlayerCharacter(results));
        }
        return characters;
    }

    @Override
    public List<PlayerCharacter> getCharactersByFullName(String fullName) {
        String sql = "SELECT pc.* FROM player_character pc " +
                "JOIN user_character uc ON pc.character_id = uc.character_id " +
                "JOIN user_details ud ON uc.user_id = ud.user_id " +
                "WHERE ud.full_name ILIKE ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + fullName + "%");
        List<PlayerCharacter> characters = new ArrayList<>();
        while (results.next()) {
            characters.add(mapRowToPlayerCharacter(results));
        }
        return characters;
    }

    private PlayerCharacter mapRowToPlayerCharacter(SqlRowSet rs) {
        PlayerCharacter playerCharacter= new PlayerCharacter();
        playerCharacter.setCharacterId(rs.getInt("character_id"));
        playerCharacter.setCharacterName(rs.getString("character_name"));
        playerCharacter.setCharacterLevel(rs.getInt("character_level"));
        playerCharacter.setSpecies(rs.getString("species"));
        playerCharacter.setCharacterClass(rs.getString("character_class"));
        playerCharacter.setSpells(rs.getString("spells"));
        playerCharacter.setWeapons(rs.getString("weapons"));
        playerCharacter.setDescription(rs.getString("description"));
        playerCharacter.setStats(rs.getString("stats"));
        return playerCharacter;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setHashedPassword(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        return user;
    }

}
