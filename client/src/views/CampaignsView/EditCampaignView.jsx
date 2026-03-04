import { useEffect, useState } from "react"; 
import { useNavigate, useParams } from "react-router-dom"; 
import CampaignService from "../../services/CampaignService"; 

import styles from "./EditCampaignView.module.css"; 

export default function EditCampaignView() { 
    const { campaignId } = useParams(); 
    const navigate = useNavigate(); 
    
    const [form, setForm] = useState({ 
        campaignName: "", 
        description: "", 
        partyId: null, 
        plotId: null,
        partyName: "",
        plotTitle: "" 
    }); 
        
        const [loading, setLoading] = useState(true); 
        
        useEffect(() => { 
            CampaignService.getCampaignById(campaignId) 
            .then(response => { 
                const c = response.data; 
                
                setForm({ 
                    campaignName: c.campaignName, 
                    description: c.description, 
                    partyId: c.partyId, 
                    plotId: c.plotId,
                    partyName: c.partyName,
                    plotTitle: c.plotTitle 
                }); 
                
                setLoading(false); 
            }) 
            .catch(err => { 
                console.error("Error loading campaign:", err); 
                navigate("/campaigns"); 
            }); 
        }, [campaignId, navigate]); 
        
        function handleChange(e) {
            const { name, value } = e.target;

            setForm({ 
                ...form, 
                [name]: name === "partyId" || name === "plotId"
                    ? Number(value)
                    : value
            });
        } 
        
        function handleSubmit(e) { 
            e.preventDefault(); 
            
            CampaignService.updateCampaign(campaignId, {
                ...form,
                campaignId: Number(campaignId)
            }) 
                .then(() => navigate(`/campaigns/${campaignId}`)) 
                .catch(err => console.error("Error updating campaign:", err)); 
        } 
        
        if (loading) return <p>Loading...</p>; 
        
        return ( 
            <div className={styles.editCampaignBody}> 
                <h1>Edit Campaign</h1> 
                
                    <form className={styles.form} onSubmit={handleSubmit}> 
                        <label> Campaign Name <br/>
                            <input 
                                name="campaignName" 
                                value={form.campaignName} 
                                onChange={handleChange} 
                                required 
                            /> 
                        </label> 
                        
                        <label> Description <br/>
                            <textarea 
                                name="description" 
                                value={form.description} 
                                onChange={handleChange} 
                            /> 
                        </label> 
                        
                        <label> Party ID <br/>
                            <input 
                                name="partyId" 
                                type="number" 
                                value={form.partyId || ""} 
                                onChange={handleChange} 
                            /> 
                        </label> 
                        
                        <label> Plot ID <br/>
                            <input 
                            name="plotId" 
                            type="number" 
                            value={form.plotId || ""} 
                            onChange={handleChange} 
                            /> 
                        </label> 
                        
                        <button type="submit">Save Changes</button> 
                    </form> 
            </div> 
    ); 
}