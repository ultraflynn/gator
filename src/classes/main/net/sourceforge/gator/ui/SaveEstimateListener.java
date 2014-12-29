package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.EstimationException;

public class SaveEstimateListener implements ActionListener
{
    private JPanel parent;
    private Estimator estimator;

    public SaveEstimateListener(JPanel parent)
    {
        this.parent = parent;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void actionPerformed(ActionEvent ev)
    {
        try {
            estimator.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (EstimationException e) {
            e.printStackTrace();
        }
    }
}
