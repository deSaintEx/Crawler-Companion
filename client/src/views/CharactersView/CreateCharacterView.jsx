import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CharacterService from "../../services/CharacterService";

import styles from './CreateCharacterView.module.css';

export default function CreateCharactersView() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        characterName: "",
        characterLevel: "",
        species: "",
        characterClass: "",
        spells: "",
        weapons: "",
        description: "",
        stats: ""
    });

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    function handleSubmit(e) {
        e.preventDefault();

        CharacterService.createCharacter(form)
            .then(() => navigate("/characters"))
            .catch(error => {
                const status = error.response?.status;

                if (status === 401 || status === 403) {
                    navigate("/login");
                    return;
                }

                console.error("Error creating character:", error);
            });
    }
    
    return (
        <>
            <div className={styles.createCharacterBody}>
                <h1>Create New Character</h1>

                <form className={`content-box ${styles.form}`} onSubmit={handleSubmit}>
                    <label>
                        Character Name <br/>
                        <input
                            name="characterName"
                            value={form.characterName}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label>
                        Level <br/>
                        <input
                            name="characterLevel"
                            value={form.characterLevel}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Species <br/>
                        <input
                            name="species"
                            value={form.species}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Class <br/>
                        <input
                            name="characterClass"
                            value={form.characterClass}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Spells <br/>
                        <input
                            name="spells"
                            value={form.spells}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Weapons <br/>
                        <input
                            name="weapons"
                            value={form.weapons}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Description <br/>
                        <textarea
                            name="description"
                            value={form.description}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Stats <br/>
                        <input
                            name="stats"
                            value={form.stats}
                            onChange={handleChange}
                        />
                    </label>

                    <button type="submit">Create Character</button>
                </form>
            </div>
        </>
    );

}