package com.techelevator.controller;

import com.techelevator.dao.PlotDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Party;
import com.techelevator.model.Plot;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/plots")
public class PlotController {

    private PlotDao plotDao;

    public PlotController(PlotDao plotDao) {
        this.plotDao = plotDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<Plot> list() {
        return plotDao.getPlots();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public Plot getById(@PathVariable int id) {
        Plot plot = plotDao.getPlotById(id);
        if (plot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plot not found.");
        } else {
            return plot;
        }
    }

    @RequestMapping(path = "/title/{title}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public Plot getByTitle(@PathVariable String title) {
        Plot plot = plotDao.getPlotByTitle(title);
        if (plot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plot not found.");
        } else {
            return plot;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public Plot add(@Valid @RequestBody Plot plot, Principal principal) {
        System.out.println(principal.getName());
        return plotDao.createPlot(plot);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public Plot update(@Valid @RequestBody Plot plot, @PathVariable int id) {
        plot.setPlotId(id);
        try {
            Plot updatedPlot = plotDao.updatePlot(plot);
            return updatedPlot;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plot not found.");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        plotDao.deletePlotById(id);
    }

}

// A future version will feature an ownership check so that authenticated users can also edit the plots they created
