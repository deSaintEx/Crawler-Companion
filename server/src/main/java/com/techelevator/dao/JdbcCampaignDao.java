package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCampaignDao implements CampaignDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCampaignDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Campaign> getCampaigns() {

        List<Campaign> campaigns = new ArrayList<>();
        String sql = "SELECT " +
                    "c.campaign_id, " +
                    "c.campaign_name, " +
                    "c.description, " +
                    "c.party_id, " +
                    "p.party_name, " +
                    "c.plot_id, " +
                    "pl.plot_title " +
                "FROM campaign c " +
                "LEFT JOIN party p ON c.party_id = p.party_id " +
                "LEFT JOIN plot pl ON c.plot_id = pl.plot_id " +
                "ORDER BY c.campaign_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                campaigns.add(mapRowToCampaign(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return campaigns;
    }

    @Override
    public Campaign getCampaignById(int campaignId) {

        Campaign campaign = null;
        String sql = "SELECT " +
                "c.campaign_id, " +
                "c.campaign_name, " +
                "c.description, " +
                "c.party_id, " +
                "p.party_name, " +
                "c.plot_id, " +
                "pl.plot_title " +
                "FROM campaign c " +
                "LEFT JOIN party p ON c.party_id = p.party_id " +
                "LEFT JOIN plot pl ON c.plot_id = pl.plot_id " +
                "WHERE campaign_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campaignId);
            if (results.next()) {
                campaign = mapRowToCampaign(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return campaign;
    }

    @Override
    public Campaign getCampaignByName(String campaignName) {

        Campaign campaign = null;
        String sql = "SELECT " +
                "c.campaign_id, " +
                "c.campaign_name, " +
                "c.description, " +
                "c.party_id, " +
                "p.party_name, " +
                "c.plot_id, " +
                "pl.plot_title " +
                "FROM campaign c " +
                "LEFT JOIN party p ON c.party_id = p.party_id " +
                "LEFT JOIN plot pl ON c.plot_id = pl.plot_id " +
                "WHERE campaign_name ILIKE ?";

        try {
            String nameToSearch = "%" + campaignName + "%";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, nameToSearch);
            if (results.next()) {
                campaign = mapRowToCampaign(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return campaign;
    }

    @Override
    public Campaign createCampaign(Campaign newCampaign) {

        int newId;
        String sql = "INSERT INTO campaign (campaign_name, description, party_id, plot_id) " +
                "VALUES (?, ?, ?, ?) RETURNING campaign_id;";

        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newCampaign.getCampaignName(), newCampaign.getDescription(),
                    newCampaign.getPartyId(), newCampaign.getPlotId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return getCampaignById(newId);
    }

    @Override
    public Campaign updateCampaign(Campaign updatedCampaign) {

        Campaign campaign = null;
        String sql = "UPDATE campaign " +
                "SET campaign_name = ?, description = ?, party_id = ?, plot_id = ? " +
                "WHERE campaign_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedCampaign.getCampaignName(),
                    updatedCampaign.getDescription(), updatedCampaign.getPartyId(),
                    updatedCampaign.getPlotId(), updatedCampaign.getCampaignId());

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least 1");
            } else {
                campaign = getCampaignById(updatedCampaign.getCampaignId());
            }
        } catch (CannotGetJdbcConnectionException e ) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return campaign;
    }

    @Override
    public int deleteCampaignById(int campaignId) {

        String sql = "DELETE FROM campaign WHERE campaign_id = ?";
        try {
            return jdbcTemplate.update(sql, campaignId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private Campaign mapRowToCampaign(SqlRowSet rs) {
        Campaign campaign = new Campaign();
        campaign.setCampaignId(rs.getInt("campaign_id"));
        campaign.setCampaignName(rs.getString("campaign_name"));
        campaign.setDescription(rs.getString("description"));
        campaign.setPartyId(rs.getInt("party_id"));
        campaign.setPartyName(rs.getString("party_name"));
        campaign.setPlotId(rs.getInt("plot_id"));
        campaign.setPlotTitle(rs.getString("plot_title"));
        return campaign;
    }

}
