import { useEffect, useState } from "react";
import CampaignService from "../../services/CampaignService";
import CampaignCard from "../../components/CampaignCard/CampaignCard";
import { useParams } from 'react-router-dom';

import styles from './CampaignDetailsView.module.css';

export default function CampaignDetailsView() {
    const [campaign, setCampaign] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const { campaignId } = useParams();

    function getPageData() {
        setIsLoading(true);
        CampaignService.getCampaignById(campaignId)
            .then((response) => {
                setCampaign(response.data);
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
    }, [campaignId]);

    return (
        <>
            {isLoading || !campaign ? (
                <p>Loading...</p>
            ) : (
                <div className={styles.campaignDetailsBody}>
                    <CampaignCard campaign={campaign} showAdminControls={true} />
                    <p>{campaign.description}</p>
                    <h3>Party: {campaign.partyName}</h3>
                    <h3>Plot: {campaign.plotTitle}</h3>
                </div>
            )}
        </>
    );
}
