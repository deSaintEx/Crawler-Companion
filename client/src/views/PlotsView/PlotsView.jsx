import { useEffect, useState } from "react";
import PlotService from "../../services/PlotService";
import PlotCard from "../../components/PlotCard/PlotCard";
import DiceLink from "../../components/DiceLink/DiceLink";

import styles from './PlotsView.module.css';

export default function PlotsView() {
    const [plots, setPlots] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    function getPageData() {
        setIsLoading(true);
        PlotService.getPlots()
            .then((response) => {
                setPlots(response.data);
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
                <div className={styles.plotsBody}>
                    <h1>Plots</h1>
                    
                    <div className={styles.plotList}>
                        {plots.map((plot) => (
                            <PlotCard key={plot.plotId} plot={plot} />
                        ))}
                    </div>
                    <br/>
                    <br/>
                    <DiceLink className={styles.createNew} to="/plots/new">Create <br/> New <br/> Plot</DiceLink>
                </div>
            )}
        </>
    );

}