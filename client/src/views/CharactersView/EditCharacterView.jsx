import { useEffect, useState } from "react"; 
import { useNavigate, useParams } from "react-router-dom"; 
import CharacterService from "../../services/CharacterService"; 

import styles from "./EditCharacterView.module.css"; 

export default function EditCharacterView() { 
    const { characterId } = useParams(); 
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
        
        const [loading, setLoading] = useState(true); 
        
        useEffect(() => { 
            CharacterService.getCharacterById(characterId) 
            .then(response => { 
                const c = response.data; 
                
                setForm({ 
                    characterName: c.characterName, 
                    characterLevel: c.characterLevel, 
                    species: c.species, 
                    characterClass: c.characterClass,
                    spells: c.spells,
                    weapons: c.weapons,
                    description: c.description,
                    stats: c.stats
                }); 
                
                setLoading(false); 
            }) 
            .catch(err => { 
                console.error("Error loading character:", err); 
                navigate("/characters"); 
            }); 
        }, [characterId, navigate]); 
        
        function handleChange(e) {
            const { name, value } = e.target;

            setForm({ 
                ...form, 
                [name]: name === "partyId" || name === "plotId"
                    ? Number(value)
                    : value
            });
        } 
        
        function handleSubmit(e) { 
            e.preventDefault(); 
            
            CharacterService.updateCharacter(characterId, {
                ...form,
                characterId: Number(characterId)
            }) 
                .then(() => navigate(`/characters/${characterId}`)) 
                .catch(err => console.error("Error updating character:", err)); 
        } 
        
        if (loading) return <p>Loading...</p>; 
        
        return ( 
            <div className={styles.editCharacterBody}> 
                <h1>Edit Character</h1> 
                
                    <form className={styles.form} onSubmit={handleSubmit}> 
                        <label> Character Name <br/>
                            <input 
                                name="characterName" 
                                value={form.characterName} 
                                onChange={handleChange} 
                                required 
                            /> 
                        </label> 
                        
                        <label> Level <br/>
                            <input 
                                name="characterLevel" 
                                value={form.characterLevel} 
                                onChange={handleChange} 
                            /> 
                        </label> 
                        
                        <label> Species <br/>
                            <input 
                                name="species" 
                                value={form.species} 
                                onChange={handleChange} 
                            /> 
                        </label> 
                        
                        <label> Class <br/>
                            <input 
                            name="characterClass"
                            value={form.characterClass} 
                            onChange={handleChange} 
                            /> 
                        </label> 

                        <label> Spells <br/>
                            <input 
                            name="spells"
                            value={form.spells} 
                            onChange={handleChange} 
                            /> 
                        </label>

                        <label> Weapons <br/>
                            <input 
                            name="weapons"
                            value={form.weapons} 
                            onChange={handleChange} 
                            /> 
                        </label>

                        <label> Description <br/>
                            <textarea 
                            name="description"
                            value={form.description} 
                            onChange={handleChange} 
                            /> 
                        </label>

                        <label> Stats <br/>
                            <textarea 
                            name="stats"
                            value={form.stats} 
                            onChange={handleChange} 
                            /> 
                        </label> 
                        
                        <button type="submit">Save Changes</button> 
                    </form> 
            </div> 
    ); 
}