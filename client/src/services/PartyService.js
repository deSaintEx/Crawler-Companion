import axios from 'axios';

export default {

    getParties() {
        return axios.get('/parties');
    },

    getPartyById(partyId) {
        return axios.get(`/parties/${partyId}`);
    },

    createParty(payload) {
        return axios.post('/parties', payload);
    }

}