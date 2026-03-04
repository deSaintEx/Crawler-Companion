import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PartyService from "../../services/PartyService";
import CharacterService from "../../services/CharacterService";

import styles from './CreatePartyView.module.css';

export default function CreatePartyView() {
    const navigate = useNavigate();
    const [allCharacters, setAllCharacters] = useState([]);

    useEffect(() => {
        CharacterService.getCharacters()
            .then(response => setAllCharacters(response.data))
            .catch(error => console.error(error));
    }, []);

    const [form, setForm] = useState({
        partyName: "",
        characterIds: []
    });

    function handleCharacterToggle(characterId) {
        setForm(previous => {
            const alreadySelected = previous.characterIds.includes(characterId);

            return {
                ...previous,
                characterIds: alreadySelected
                    ? previous.characterIds.filter(id => id !== characterId)
                    : [...previous.characterIds, characterId]
            };
        });
    }

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    function handleSubmit(e) {
        e.preventDefault();

        const payload = {
            partyName: form.partyName,
            characterIds: form.characterIds
        };

        PartyService.createParty(payload)
            .then(() => navigate("/parties"))
            .catch(error => console.error(error));
    }
    
    return (
        <>
            <div className={styles.createPartyBody}>
                <h1>Create New Party</h1>

                <form className={styles.form} onSubmit={handleSubmit}>
                    <label>
                        Party Name <br/>
                        <input
                            name="partyName"
                            value={form.partyName}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <h3>Select Characters</h3>
                    <ul>
                        {allCharacters.map(character => (
                            <li key={character.characterId}>
                                <label>
                                    <input
                                        type="checkbox"
                                        checked={form.characterIds.includes(character.characterId)}
                                        onChange={() => handleCharacterToggle(character.characterId)}
                                    />
                                    {character.characterName}
                                </label>
                            </li>
                        ))}
                    </ul>

                    <button type="submit">Create Party</button>
                </form>
            </div>
        </>
    );

}