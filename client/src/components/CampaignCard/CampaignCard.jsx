import styles from './CampaignCard.module.css';
import { NavLink } from 'react-router-dom';
import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';
import DiceLink from '../DiceLink/DiceLink';
import { useNavigate } from 'react-router-dom';
import CampaignService from '../../services/CampaignService';



export default function CampaignCard({ campaign, showAdminControls }) {

    const navigate = useNavigate();
    const { user } = useContext(UserContext);
    const isAdmin = user?.role?.includes("ADMIN");

    function handleDelete(id) {

        if (!window.confirm("Are you sure you want to delete this campaign?")) return; 
        
        CampaignService.deleteCampaign(id) 
            .then(() => navigate("/campaigns")) 
            .catch(err => console.error("Delete failed:", err)); 
    }

    return (
        
        <article className={styles.campaignCard}>
            <NavLink className={styles.campaignName} to={`/campaigns/${campaign.campaignId}`}>{campaign.campaignName}</NavLink>

            {isAdmin && showAdminControls && ( 
                <div className={styles.adminControls}> 
                    <DiceLink to={`/campaigns/${campaign.campaignId}/edit`}> 
                        Edit 
                    </DiceLink> 
                    <DiceLink onClick={() => handleDelete(campaign.campaignId)}> 
                        Delete 
                    </DiceLink> 
                </div> )}
        </article>
    
    );
}