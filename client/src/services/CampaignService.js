import axios from 'axios';

export default {

    getCampaigns() {
        return axios.get('/campaigns');
    },

    getCampaignById(campaignId) {
        return axios.get(`/campaigns/${campaignId}`);
    },

    createCampaign(campaign) {
        return axios.post('/campaigns', campaign);
    },

    updateCampaign(campaignId, payload) { 
        return axios.put(`/campaigns/${campaignId}`, payload); 
    },

    deleteCampaign(id) { 
        return axios.delete(`/campaigns/${id}`); 
    }

}