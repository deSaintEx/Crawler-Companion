import axios from 'axios';

export default {

    getCharacters() {
        return axios.get('/characters');
    },

    getCharacterById(characterId) {
        return axios.get(`/characters/${characterId}`);
    },

    createCharacter(character) {
        return axios.post('/characters', character);
    },

    updateCharacter(characterId, payload) { 
        return axios.put(`/characters/${characterId}`, payload); 
    },

    deleteCharacter(id) { 
        return axios.delete(`/characters/${id}`); 
    }

}