import styles from './CharacterCard.module.css';
import { NavLink } from 'react-router-dom';
import CharacterService from '../../services/CharacterService';
import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';
import { useNavigate } from 'react-router-dom';
import DiceLink from '../DiceLink/DiceLink';

export default function CharacterCard({ character, showAdminControls }) {

    const navigate = useNavigate();
    const { user } = useContext(UserContext);
    const isAdmin = user?.role?.includes("ADMIN");

    function handleDelete(id) {

        if (!window.confirm("Are you sure you want to delete this character?")) return; 
        
        CharacterService.deleteCharacter(id) 
            .then(() => navigate("/characters")) 
            .catch(err => console.error("Delete failed:", err)); 
    }

    return (
        <article className={styles.characterCard}>
            <NavLink className={styles.characterName} to={`/characters/${character.characterId}`}>{character.characterName}</NavLink>

            {isAdmin && showAdminControls && ( 
                <div className={styles.adminControls}> 
                    <DiceLink to={`/characters/${character.characterId}/edit`}> 
                        Edit 
                    </DiceLink> 
                    <DiceLink onClick={() => handleDelete(character.characterId)}> 
                        Delete 
                    </DiceLink> 
                </div> )}
        </article>
    );
}