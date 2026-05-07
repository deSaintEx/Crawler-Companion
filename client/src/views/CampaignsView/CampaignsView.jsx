import { useEffect, useState } from "react";
import CampaignService from "../../services/CampaignService";
import CampaignCard from "../../components/CampaignCard/CampaignCard";
import { useContext } from "react";
import { UserContext } from "../../context/UserContext";

import styles from './CampaignsView.module.css';
import DiceLink from "../../components/DiceLink/DiceLink";

export default function CampaignsView() {
    const [campaigns, setCampaigns] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const { user } = useContext(UserContext);
    const isAdmin = user?.role?.includes("ADMIN");

    function getPageData() {
        setIsLoading(true);
        CampaignService.getCampaigns()
            .then((response) => {
                setCampaigns(response.data);
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
                <div className={styles.campaignsBody}>
                    <h1>Campaigns</h1>

                    <div className={"content-box"}>
                        {campaigns.map((campaign) => (
                            <CampaignCard className={styles.campaignCard} key={campaign.campaignId} campaign={campaign} showAdminControls={false} />
                        ))}
                    </div>
                    <br/>
                    <br/>
                    {isAdmin && (
                        <DiceLink className={styles.createNew} to="/campaigns/new">Create <br/> New <br/> Campaign</DiceLink>
                    )}    
                </div>
            )}
        </>
    );

}