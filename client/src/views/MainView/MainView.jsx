import { useState } from 'react';
import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink } from 'react-router-dom';

import styles from './MainView.module.css';

export default function MainView() {
  const [cardView, setCardView] = useState(true);

  return (
    <div className={styles.mainBody}>
      <div>
        <header>Crawler Companion</header>
      
        <div className={styles.menu}>
          <NavLink className={styles.menuItem} to="/campaigns">Campaigns</NavLink>
          <NavLink className={styles.menuItem} to="/characters">Characters</NavLink>
          <NavLink className={styles.menuItem} to="/parties">Parties</NavLink>
          <NavLink className={styles.menuItem} to="/plots">Plots</NavLink>
        </div>
        <br/>
            {/* <p>
                  Below are two icons: one to indicate a &quot;tile&quot; view of products, and
                  another to indicate a &quot;table&quot; view.
                </p> */}
            {/* <p>
                  The state variable <code>cardView</code> tracks which view is the active
                  one.
                </p> */}
        {/* <div className={styles.cardViewIcons}>
          <FontAwesomeIcon
            icon="fa-solid fa-grip"
            className={`${styles.viewIcon} ${cardView ? styles.active : ''}`}
            onClick={() => setCardView(true)}
            title="View tiles"
            />
          <FontAwesomeIcon
            icon="fa-solid fa-table"
            className={`${styles.viewIcon} ${!cardView ? styles.active : ''}`}
            onClick={() => setCardView(false)}
            title="View table"
          />
        </div> */}
      </div>
    </div>  
  );
}
