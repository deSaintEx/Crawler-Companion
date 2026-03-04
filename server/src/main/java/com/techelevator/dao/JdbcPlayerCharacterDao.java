package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.PlayerCharacter;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlayerCharacterDao implements PlayerCharacterDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPlayerCharacterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PlayerCharacter> getPlayerCharacters() {

        List<PlayerCharacter> playerCharacters = new ArrayList<>();
        String sql = "SELECT character_id, character_name, character_level, species, character_class, " +
                "spells, weapons, description, stats " +
                "FROM player_character " +
                "ORDER BY character_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                playerCharacters.add(mapRowToPlayerCharacter(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return playerCharacters;
    }

    @Override
    public PlayerCharacter getPlayerCharacterById(int characterId) {

        PlayerCharacter playerCharacter = null;
        String sql = "SELECT character_id, character_name, character_level, species, character_class, " +
                "spells, weapons, description, stats FROM player_character " +
                "WHERE character_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, characterId);
            if (results.next()) {
                playerCharacter = mapRowToPlayerCharacter(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return playerCharacter;
    }

    @Override
    public PlayerCharacter getPlayerCharacterByName(String characterName) {
        PlayerCharacter playerCharacter = null;
        String sql = "SELECT character_id, character_name, character_level, species, character_class, " +
                "spells, weapons, description, stats FROM player_character " +
                "WHERE character_name ILIKE ?";

        try {
            String nameToSearch = "%" + characterName + "%";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, nameToSearch);
            if (results.next()) {
                playerCharacter = mapRowToPlayerCharacter(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return playerCharacter;
    }

    @Override
    public PlayerCharacter createPlayerCharacter(PlayerCharacter newPlayerCharacter) {

        int newId;
        String sql = "INSERT INTO player_character (character_name, character_level, species, character_class, " +
                "spells, weapons, description, stats) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING character_id;";

        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newPlayerCharacter.getCharacterName(),
                    newPlayerCharacter.getCharacterLevel(), newPlayerCharacter.getSpecies(),
                    newPlayerCharacter.getCharacterClass(), newPlayerCharacter.getSpells(),
                    newPlayerCharacter.getWeapons(), newPlayerCharacter.getDescription(),
                    newPlayerCharacter.getStats());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return getPlayerCharacterById(newId);
    }

    @Override
    public PlayerCharacter updatePlayerCharacter(PlayerCharacter updatedPlayerCharacter) {

        PlayerCharacter playerCharacter = null;
        String sql = "UPDATE player_character " +
                "SET character_name = ?, character_level = ?, species = ?, character_class = ?, spells = ?, " +
                "weapons = ?, description = ?, stats = ? " +
                "WHERE character_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedPlayerCharacter.getCharacterName(),
                    updatedPlayerCharacter.getCharacterLevel(), updatedPlayerCharacter.getSpecies(),
                    updatedPlayerCharacter.getCharacterClass(), updatedPlayerCharacter.getSpells(),
                    updatedPlayerCharacter.getWeapons(), updatedPlayerCharacter.getDescription(),
                    updatedPlayerCharacter.getStats(), updatedPlayerCharacter.getCharacterId());

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least 1");
            } else {
                playerCharacter = getPlayerCharacterById(updatedPlayerCharacter.getCharacterId());
            }
        } catch (CannotGetJdbcConnectionException e ) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return playerCharacter;
    }

    @Override
    public int deletePlayerCharacterById(int characterId) {

        String sql = "DELETE FROM player_character WHERE character_id = ?";
        try {
            return jdbcTemplate.update(sql, characterId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
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

}
