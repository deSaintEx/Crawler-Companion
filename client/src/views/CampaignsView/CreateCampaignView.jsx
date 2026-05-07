import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CampaignService from "../../services/CampaignService";

import styles from './CreateCampaignView.module.css';

export default function CreateCampaignView() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        campaignName: "",
        description: "",
        partyId: "",
        plotId: ""
    });

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    function handleSubmit(e) {
        e.preventDefault();

        CampaignService.createCampaign(form)
            .then(() => navigate("/campaigns"))
            .catch(error => {
                const status = error.response?.status;

                if (status === 401 || status === 403) {
                    navigate("/login");
                    return;
                }

                console.error("Error creating campaign:", error);
            });
    }
    
    return (
        <>
            <div className={styles.createCampaignBody}>
                <h1>Create New Campaign</h1>

                <form className={`content-box ${styles.form}`} onSubmit={handleSubmit}>
                    <label>
                        Campaign Name <br/>
                        <input
                            name="campaignName"
                            value={form.campaignName}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label>
                        Description <br/>
                        <textarea
                            name="description"
                            value={form.description}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Party ID <br/>
                        <input
                            name="partyId"
                            value={form.partyId}
                            onChange={handleChange}
                        />
                    </label>

                    <label>
                        Plot ID <br/>
                        <input
                            name="plotId"
                            value={form.plotId}
                            onChange={handleChange}
                        />
                    </label>

                    <button type="submit">Create Campaign</button>
                </form>
            </div>
        </>
    );

}