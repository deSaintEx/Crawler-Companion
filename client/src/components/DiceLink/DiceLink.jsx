import { NavLink, useNavigate } from "react-router-dom";
import { useState } from "react"; 

import styles from "./DiceLink.module.css"; 

export default function DiceLink({ to, children, className = "", onClick }) { 
    const [rolling, setRolling] = useState(false); 
    
    function handleClick(e) { 
        setRolling(true); 
        setTimeout(() => setRolling(false), 600);
        
        if (onClick) {
            e.preventDefault();
            onClick(e);
        }
    }
    
    if (to) {
    
    return ( 
        <NavLink 
            to={to} 
            className={`${styles.link} ${rolling ? styles.roll : ""} ${className}`} 
            onClick={handleClick} 
        > 
            {children} 
        </NavLink> 
        ); 
    }

    return (

        <button
            className={`${styles.diceReset} ${styles.link} ${rolling ? styles.roll : ""} ${className}`} 
            onClick={handleClick}
        >
            {children}
        </button>
    );
}