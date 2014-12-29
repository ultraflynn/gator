package net.sourceforge.gator.ui;

import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.EstimationException;

public class OpenEstimateListener implements ActionListener
{
    private JPanel parent;
    private Estimator estimator;
    private MainUI mainUI;

    public OpenEstimateListener(JPanel parent)
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
            // FIXME If the estimate has changed confirm that it is saved or not
            OpenEstimateDialog dialog = new OpenEstimateDialog(estimator);
            File selectedEstimate = dialog.show(parent);

            if (selectedEstimate != null) {
                String name = selectedEstimate.getName();
                String ref = selectedEstimate.getParentFile().getName();
                File fullName = new File(ref, name);
                estimator.load(fullName.toString());
                mainUI.reload();
            }
        } catch (EstimationException e) {
            e.printStackTrace();
        }
    }
}
