import styles from './PartyCard.module.css';
import { NavLink } from 'react-router-dom';

export default function PartyCard({ party }) {
    return (
        
        <article className={styles.partyCard}>
            <NavLink className={styles.partyName} to={`/parties/${party.partyId}`}>{party.partyName}</NavLink>
        </article>
    
    );
}