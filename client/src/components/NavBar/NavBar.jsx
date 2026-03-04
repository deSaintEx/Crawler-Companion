import DiceLink from '../DiceLink/DiceLink';
import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';

import styles from './NavBar.module.css';

export default function NavBar() {
  const { user } = useContext(UserContext);
  const isLoggedIn = user !== null;

  return (
    <nav className={styles.nav}>
      
      <DiceLink to="/">Home</DiceLink>

      {isLoggedIn ? (
        <DiceLink to="/logout">Logout</DiceLink>
      ) : (
        <DiceLink to="/login">Login</DiceLink>
      )}
    </nav>
  );
}
