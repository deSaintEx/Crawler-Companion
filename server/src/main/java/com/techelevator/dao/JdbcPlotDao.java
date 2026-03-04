package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Party;
import com.techelevator.model.Plot;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlotDao implements PlotDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPlotDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Plot> getPlots() {

        List<Plot> plots = new ArrayList<>();
        String sql = "SELECT plot_id, plot_title, description " +
                "FROM plot " +
                "ORDER BY plot_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                plots.add(mapRowToPlot(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return plots;
    }

    @Override
    public Plot getPlotById(int plotId) {

        Plot plot = null;
        String sql = "SELECT plot_id, plot_title, description " +
                "FROM plot " +
                "WHERE plot_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, plotId);
            if (results.next()) {
                plot = mapRowToPlot(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return plot;
    }

    @Override
    public Plot getPlotByTitle(String plotTitle) {

        Plot plot = null;
        String sql = "SELECT plot_id, plot_title, description " +
                "FROM plot " +
                "WHERE plot_title ILIKE ?";

        try {
            String nameToSearch = "%" + plotTitle + "%";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, nameToSearch);
            if (results.next()) {
                plot = mapRowToPlot(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return plot;
    }

    @Override
    public Plot createPlot(Plot newPlot) {

        int newId;
        String sql = "INSERT INTO plot (plot_title, description) " +
                "VALUES (?, ?) RETURNING plot_id;";

        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newPlot.getPlotTitle(), newPlot.getDescription());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }

        return getPlotById(newId);
    }

    @Override
    public Plot updatePlot(Plot updatedPlot) {

        Plot plot = null;
        String sql = "UPDATE plot " +
                "SET plot_title = ?, description = ? " +
                "WHERE plot_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedPlot.getPlotTitle(),
                    updatedPlot.getDescription(), updatedPlot.getPlotId());

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least 1");
            } else {
                plot = getPlotById(updatedPlot.getPlotId());
            }
        } catch (CannotGetJdbcConnectionException e ) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
        return plot;
    }

    @Override
    public int deletePlotById(int plotId) {

        String sql = "DELETE FROM plot WHERE plot_id = ?";
        try {
            return jdbcTemplate.update(sql, plotId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private Plot mapRowToPlot(SqlRowSet rs) {
        Plot plot = new Plot();
        plot.setPlotId(rs.getInt("plot_id"));
        plot.setPlotTitle(rs.getString("plot_title"));
        plot.setDescription(rs.getString("description"));
        return plot;
    }

}
