import styles from './PlotCard.module.css';
import { NavLink } from 'react-router-dom';

export default function PlotCard({ plot }) {
    return (
        <article className={styles.plotCard}>
            <h2 className={styles.plotTitle}>{plot.plotTitle}</h2>
            <p className={styles.plotDescription}>{plot.description}</p>
        </article>
    );
}