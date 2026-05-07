import { useEffect, useState } from "react";
import PartyService from "../../services/PartyService";
import PartyCard from "../../components/PartyCard/PartyCard";
import CharacterCard from "../../components/CharacterCard/CharacterCard";
import { useParams } from 'react-router-dom';

import styles from './PartyDetailsView.module.css';

export default function PartyDetailsView() {
    const [party, setParty] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const { partyId } = useParams();
    const [characters, setCharacters] = useState([]);

    function getPageData() {
        setIsLoading(true);
        PartyService.getPartyById(partyId)
            .then((response) => {
                setParty(response.data);
                setCharacters(response.data.characters || []);
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
    }, [partyId]);

    return (
        <>
            {isLoading || !party ? (
                <p>Loading...</p>
            ) : (
                <div className={`content-box`}>
                    <PartyCard party={party} />
                    <h3>Characters:</h3>
                    {characters.length === 0 ? (
                        <p>No characters are assigned to this party yet</p>
                    ) : (
                        <ul className={styles.partyCharactersList}>
                            {characters.map(character => (
                                <li key={character.characterId}>
                                    <CharacterCard character={character} />
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            )}
        </>
    );
}
