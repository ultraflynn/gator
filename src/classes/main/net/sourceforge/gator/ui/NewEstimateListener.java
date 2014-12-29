package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Estimator;

public class NewEstimateListener implements ActionListener
{
    private JPanel parent;
    private Estimator estimator;
    private MainUI mainUI;

    public NewEstimateListener(JPanel parent)
    {
        this.parent = parent;
    }

    public void setMainUI(MainUI mainUI)
    {
        this.mainUI = mainUI;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void actionPerformed(ActionEvent ev)
    {
        try {
            // FIXME confirm with the user that this is OK
            estimator.create();
            mainUI.reload();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
