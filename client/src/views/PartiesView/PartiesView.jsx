import { useEffect, useState } from "react";
import PartyService from "../../services/PartyService";
import PartyCard from "../../components/PartyCard/PartyCard";
import DiceLink from "../../components/DiceLink/DiceLink";
import { useContext } from "react";
import { UserContext } from "../../context/UserContext";

import styles from './PartiesView.module.css';

export default function PartiesView() {
    const [parties, setParties] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const { user } = useContext(UserContext);
    const isAdmin = user?.role?.includes("ADMIN");

    function getPageData() {
        setIsLoading(true);
        PartyService.getParties()
            .then((response) => {
                setParties(response.data);
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
                <div className={styles.partiesBody}>
                    <h1>Parties</h1>

                    <div className={styles.partyList}>
                        {parties.map((party) => (
                            <PartyCard key={party.partyId} party={party} />
                        ))}
                    </div>
                    <br/>
                    <br/>
                    {isAdmin && (
                        <DiceLink className={styles.createNew} to="/parties/new">Create <br/> New <br/> Party</DiceLink>
                    )}    
                </div>
            )}
        </>
    );

}