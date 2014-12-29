package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Item;
import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Estimator;

import net.sourceforge.gator.util.ClassUtils;

public class AddItemListener implements ActionListener
{
    private JPanel parent;
    private JTree tree;
    private Estimator estimator;
    private EstimateTabPage page;

    public AddItemListener(JPanel parent)
    {
        this.parent = parent;
    }

    public void setTree(JTree tree)
    {
        this.tree = tree;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void setEstimateTabPage(EstimateTabPage page)
    {
        this.page = page;
    }

    public void actionPerformed(ActionEvent ev)
    {
        ItemDialog dialog = new ItemDialog(estimator);
        Item item = dialog.show(parent);

        TreePath path = tree.getLeadSelectionPath();
        Object obj = path.getLastPathComponent();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
        Object uo = node.getUserObject();

        if (ClassUtils.isImplementor(uo, "net.sourceforge.gator.Category")) {
            Category category = (Category) uo;
            category.addItem(item);
        }

        try {
            estimator.saveConfiguration();
            page.loadConfigTree();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

