package com.techelevator.controller;

import com.techelevator.dao.CampaignDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import com.techelevator.model.Party;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private CampaignDao campaignDao;

    public CampaignController(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<Campaign> list() {
        return campaignDao.getCampaigns();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public Campaign getById(@PathVariable int id) {
        Campaign campaign = campaignDao.getCampaignById(id);
        if (campaign == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found.");
        } else {
            return campaign;
        }
    }

    @RequestMapping(path = "/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public Campaign getByName(@PathVariable String name) {
        Campaign campaign = campaignDao.getCampaignByName(name);
        if (campaign == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found.");
        } else {
            return campaign;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public Campaign add(@Valid @RequestBody Campaign campaign, Principal principal) {
        System.out.println(principal.getName());
        return campaignDao.createCampaign(campaign);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public Campaign update(@Valid @RequestBody Campaign campaign, @PathVariable int id) {
        campaign.setCampaignId(id);
        try {
            Campaign updatedCampaign = campaignDao.updateCampaign(campaign);
            return updatedCampaign;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found.");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        campaignDao.deleteCampaignById(id);
    }

}
