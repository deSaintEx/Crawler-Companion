package com.techelevator.dao;

import com.techelevator.model.Plot;

import java.util.List;

public interface PlotDao {

    List<Plot> getPlots();

    Plot getPlotById(int plotId);

    Plot getPlotByTitle(String plotTitle);

    Plot createPlot(Plot newPlot);

    Plot updatePlot(Plot updatedPlot);

    int deletePlotById(int plotId);

}
