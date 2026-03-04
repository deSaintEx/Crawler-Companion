package com.techelevator.dao;

import com.techelevator.model.Campaign;

import java.util.List;

public interface CampaignDao {

    List<Campaign> getCampaigns();

    Campaign getCampaignById(int campaignId);

    Campaign getCampaignByName(String campaignName);

    Campaign createCampaign(Campaign newCampaign);

    Campaign updateCampaign(Campaign updatedCampaign);

    int deleteCampaignById(int campaignId);

}
