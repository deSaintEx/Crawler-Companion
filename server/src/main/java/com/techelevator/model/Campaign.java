package com.techelevator.model;

import java.util.Objects;

public class Campaign {

    private int campaignId;
    private String campaignName;
    private String description;
    private int partyId;
    private String partyName;
    private int plotId;
    private String plotTitle;

    public Campaign() { }

    public Campaign(int campaignId, String campaignName, String description, int partyId, String partyName, int plotId, String plotTitle) {
        this.campaignId = campaignId;
        this.campaignName = campaignName;
        this.description = description;
        this.partyId = partyId;
        this.partyName = partyName;
        this.plotId = plotId;
        this.plotTitle = plotTitle;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return campaignId == campaign.campaignId &&
                Objects.equals(campaignName, campaign.campaignName) &&
                Objects.equals(description, campaign.description) &&
                partyId == campaign.partyId &&
                plotId == campaign.plotId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId, campaignName, description, partyId, plotId);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "campaignId=" + campaignId +
                ", campaignName='" + campaignName + '\'' +
                ", description=" + description + '\'' +
                ", partyId=" + partyId + '\'' +
                ", plotId=" + plotId + '\'' +
                '}';
    }

}
