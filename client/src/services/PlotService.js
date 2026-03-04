import axios from 'axios';

export default {

    getPlots() {
        return axios.get('/plots');
    },

    createPlot(plot) {
        return axios.post('/plots', plot)
    }

}