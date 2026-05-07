import { useEffect, useState } from "react";
import CharacterService from "../../services/CharacterService";
import CharacterCard from "../../components/CharacterCard/CharacterCard";
import CreateCharacterView from "./CreateCharacterView";
import DiceLink from "../../components/DiceLink/DiceLink";

import styles from './CharactersView.module.css';

export default function CharactersView() {
    const [characters, setCharacters] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    function getPageData() {
        setIsLoading(true);
        CharacterService.getCharacters()
            .then((response) => {
                setCharacters(response.data);
                setIsLoading(false);
            })
            .catch((error) => {
                const message = error.response?.data?.message || error.message;
                console.error(`Error fetching characters: ${message}`);
                setIsLoading(false);
            });
    }

    useEffect(() => {
        getPageData();
    }, []);
    
    return (
        <>
            {isLoading ? (
                <p>Loading...</p>
            ) : (
                <div className={styles.charactersBody}>
                    <h1>Characters</h1>

                    <div className={"content-box"}>
                        {characters.map((character) => (
                            <CharacterCard key={character.characterId} character={character} showAdminControls={false} />
                        ))}
                    </div>
                    <br/>
                    <br/>
                    <DiceLink className={styles.createNew} to="/characters/new">Create <br/> New <br/> Character</DiceLink>
                </div>
            )}
        </>
    );

}