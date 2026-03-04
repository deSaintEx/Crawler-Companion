package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Party;
import com.techelevator.model.PlayerCharacter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPartyDao implements PartyDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPartyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Party> getParties() {

        List<Party> parties = new ArrayList<>();
        String sql = "SELECT party_id, party_name " +
                "FROM party " +
                "ORDER BY party_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                parties.add(mapRowToParty(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return parties;
    }

    @Override
    public Party getPartyById(int partyId) {

        Party party = null;
        String sql = "SELECT party_id, party_name " +
                "FROM party " +
                "WHERE party_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
            if (results.next()) {
                party = mapRowToParty(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return party;
    }

    @Override
    public Party getPartyByName(String partyName) {

        Party party = null;
        String sql = "SELECT party_id, party_name " +
                "FROM party " +
                "WHERE party_name ILIKE ?";

        try {
            String nameToSearch = "%" + partyName + "%";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, nameToSearch);
            if (results.next()) {
                party = mapRowToParty(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return party;
    }

    @Override
    public List<PlayerCharacter> getCharactersByPartyId(int partyId) {

        List<PlayerCharacter> characters = new ArrayList<>();
        String sql =
                "SELECT pc.character_id, pc.character_name, pc.character_level, pc.species, pc.character_class, pc.spells, pc.weapons, pc.description, pc.stats " +
                        "FROM party_characters pcx " +
                        "JOIN player_character pc ON pcx.character_id = pc.character_id " +
                        "WHERE pcx.party_id = ? " +
                        "ORDER BY pc.character_name;";

        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, partyId);
            while (rs.next()) {
                characters.add(mapRowToPlayerCharacter(rs));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return characters;

    }

    @Override
    public Party createParty(Party newParty) {

        int newId;
        String sql = "INSERT INTO party (party_name) " +
                "VALUES (?) RETURNING party_id;";

        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newParty.getPartyName());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return getPartyById(newId);
    }

    @Override
    public Party updateParty(Party updatedParty) {

        Party party = null;
        String sql = "UPDATE party " +
                "SET party_name = ? " +
                "WHERE party_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedParty.getPartyName(), updatedParty.getPartyId());

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least 1");
            } else {
                party = getPartyById(updatedParty.getPartyId());
            }
        } catch (CannotGetJdbcConnectionException e ) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return party;
    }

    @Override
    public void addCharacterToParty(int partyId, int characterId) {

        String sql = "INSERT INTO party_characters (party_id, character_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, partyId, characterId);
        } catch (CannotGetJdbcConnectionException | DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public int deletePartyById(int partyId) {

        String sql = "DELETE FROM party WHERE party_id = ?";
        try {
            return jdbcTemplate.update(sql, partyId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private Party mapRowToParty(SqlRowSet rs) {
        Party party = new Party();
        party.setPartyId(rs.getInt("party_id"));
        party.setPartyName(rs.getString("party_name"));
        return party;
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
