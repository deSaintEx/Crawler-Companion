package com.techelevator.dao;

import com.techelevator.model.Party;
import com.techelevator.model.PlayerCharacter;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPartyCharactersDao implements PartyCharactersDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPartyCharactersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void assignCharacterToParty(int partyId, int characterId) {
        String sql = "INSERT INTO party_characters (party_id, character_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, partyId, characterId);
    }

    @Override
    public void removeCharacterFromParty(int partyId, int characterId) {
        String sql = "DELETE FROM party_characters WHERE party_id = ? AND character_id = ?";
        jdbcTemplate.update(sql, partyId, characterId);
    }

    @Override
    public List<PlayerCharacter> getCharactersByPartyId(int partyId) {
        String sql = "SELECT pc.* FROM player_character pc " +
                "JOIN party_characters py ON pc.character_id = py.character_id " +
                "WHERE py.party_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
        List<PlayerCharacter> characters = new ArrayList<>();
        while (results.next()) {
            characters.add(mapRowToPlayerCharacter(results));
        }
        return characters;
    }

    @Override
    public List<Party> getPartiesByCharacterId(int characterId) {
        String sql = "SELECT p.* FROM party p " +
                "JOIN party_characters pc ON p.party_id = pc.party_id " +
                "WHERE pc.character_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, characterId);
        List<Party> parties = new ArrayList<>();
        while (results.next()) {
            parties.add(mapRowToParty(results));
        }
        return parties;
    }

    @Override
    public List<Party> getPartiesByUsername(String username) {
        String sql = "SELECT p.* FROM party p " +
                "JOIN party_characters pc ON p.party_id = pc.party_id " +
                "JOIN user_character uc ON pc.character_id = uc.character_id " +
                "JOIN users u ON uc.user_id = u.user_id " +
                "WHERE u.username ILIKE ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + username + "%");
        List<Party> parties = new ArrayList<>();
        while (results.next()) {
            parties.add(mapRowToParty(results));
        }
        return parties;
    }

    @Override
    public List<Party> getPartiesByFullName(String fullName) {
        String sql = "SELECT p.* FROM party p " +
                "JOIN party_characters pc ON p.party_id = pc.party_id " +
                "JOIN user_character uc ON pc.character_id = uc.character_id " +
                "JOIN user_details ud ON uc.user_id = ud.user_id " +
                "WHERE ud.full_name ILIKE ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + fullName + "%");
        List<Party> parties = new ArrayList<>();
        while (results.next()) {
            parties.add(mapRowToParty(results));
        }
        return parties;
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

    private Party mapRowToParty(SqlRowSet rs) {
        Party party = new Party();
        party.setPartyId(rs.getInt("party_id"));
        party.setPartyName(rs.getString("party_name"));
        return party;
    }

}
