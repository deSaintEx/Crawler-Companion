package com.techelevator.model;

import java.util.Objects;

public class Plot {

    private int plotId;
    private String plotTitle;
    private String description;

    public Plot() { }

    public Plot(int plotId, String plotTitle, String description) {
        this.plotId = plotId;
        this.plotTitle = plotTitle;
        this.description = description;
    }

    public int getPlotId() {
        return plotId;
    }

    public void setPlotId(int plotId) {
        this.plotId = plotId;
    }

    public String getPlotTitle() {
        return plotTitle;
    }

    public void setPlotTitle(String plotTitle) {
        this.plotTitle = plotTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plot plot = (Plot) o;
        return plotId == plot.plotId &&
                Objects.equals(plotTitle, plot.plotTitle) &&
                Objects.equals(description, plot.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plotId, plotTitle, description);
    }

    @Override
    public String toString() {
        return "Plot{" +
                "plotId=" + plotId + '\'' +
                ", plotTitle='" + plotTitle + '\'' +
                ", description=" + description + '\'' +
                '}';
    }

}
