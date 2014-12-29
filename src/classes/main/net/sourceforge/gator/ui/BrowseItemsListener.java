package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Estimator;

public class BrowseItemsListener implements ActionListener
{
    private EstimateTabPage parent;
    private Estimator estimator;

    public BrowseItemsListener(EstimateTabPage parent)
    {
        this.parent = parent;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void actionPerformed(ActionEvent ev)
    {
        BrowseItemsDialog dialog = new BrowseItemsDialog(estimator);
        dialog.show(parent);

        try {
            estimator.saveConfiguration();
            parent.loadConfigTree();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
