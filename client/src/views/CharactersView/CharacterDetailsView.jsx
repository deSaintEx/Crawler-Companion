import { useEffect, useState } from "react";
import CharacterService from "../../services/CharacterService";
import CharacterCard from "../../components/CharacterCard/CharacterCard";
import { useParams } from 'react-router-dom';

import styles from './CharacterDetailsView.module.css';

export default function CharacterDetailsView() {
    const [character, setCharacter] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const { characterId } = useParams();

    function getPageData() {
        setIsLoading(true);
        CharacterService.getCharacterById(characterId)
            .then((response) => {
                setCharacter(response.data);
                setIsLoading(false);
            })
            .catch((error) => {
                const errorMessage = error.response ? error.response.data.message : error.message;
                console.error(errorMessage);
                setIsLoading(false);
            });
    }

    useEffect(() => {
        getPageData();
    }, [characterId]);

    return (
        <>
            {isLoading || !character ? (
                <p>Loading...</p>
            ) : (
                <div className={styles.characterDetailsBody}>
                    <CharacterCard character={character} showAdminControls={true} />
                    <p>{character.description}</p>
                    <h3>Level: {character.characterLevel}</h3>
                    <h3>Species: {character.species}</h3>
                    <h3>Class: {character.characterClass}</h3>
                    <h3>Spells: {character.spells}</h3>
                    <h3>Weapons: {character.weapons}</h3>
                    <h3>Stats: {character.stats}</h3>
                </div>
            )}
        </>
    );
}
