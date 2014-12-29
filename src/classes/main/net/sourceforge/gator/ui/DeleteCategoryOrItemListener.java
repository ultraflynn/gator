package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Item;

import net.sourceforge.gator.util.ClassUtils;

public class DeleteCategoryOrItemListener implements ActionListener
{
    private JPanel parent;
    private JTree tree;
    private Estimator estimator;
    private EstimateTabPage page;

    public DeleteCategoryOrItemListener(JPanel parent)
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
        TreePath path = tree.getLeadSelectionPath();
        Object obj = path.getLastPathComponent();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
        Object uo1 = node.getUserObject();

        TreePath parentPath = path.getParentPath();
        Object parentObj = parentPath.getLastPathComponent();
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parentObj;
        Object uo2 = parentNode.getUserObject();

        if (ClassUtils.isImplementor(uo2, "net.sourceforge.gator.Category")) {
            Category parentCategory = (Category) uo2;

            if (ClassUtils.isImplementor(uo1, "net.sourceforge.gator.Category")) {
                Category category = (Category) uo1;
                parentCategory.removeSubCategory(category);
            } else if (ClassUtils.isImplementor(uo1, "net.sourceforge.gator.Item")) {
                Item item = (Item) uo1;
                parentCategory.removeItem(item);
            }
        } else {
            Configuration config = estimator.getConfiguration();
            if (ClassUtils.isImplementor(uo1, "net.sourceforge.gator.Category")) {
                Category category = (Category) uo1;
                config.removeCategory(category);
            }
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
