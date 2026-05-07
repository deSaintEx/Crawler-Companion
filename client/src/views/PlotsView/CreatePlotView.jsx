import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PlotService from "../../services/PlotService";

import styles from './CreatePlotView.module.css';

export default function CreatePlotView() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        plotTitle: "",
        description: ""
    });

    function handleChange(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    function handleSubmit(e) {
        e.preventDefault();

        PlotService.createPlot(form)
            .then(() => navigate("/plots"))
            .catch(error => {
                const status = error.response?.status;

                if (status === 401 || status === 403) {
                    navigate("/login");
                    return;
                }

                console.error("Error creating plot:", error);
            });
    }
    
    return (
        <>
            <div className={styles.createPlotBody}>
                <h1>Create New Plot</h1>

                <form className={`content-box ${styles.form}`} onSubmit={handleSubmit}>
                    <label>
                        Plot Title <br/>
                        <input
                            name="plotTitle"
                            value={form.plotTitle}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    <br/>

                    <label>
                        Description <br/>
                        <textarea
                            name="description"
                            value={form.description}
                            onChange={handleChange}
                        />
                    </label>
                    <br/>

                    <button type="submit">Create Plot</button>
                </form>
            </div>
        </>
    );

}