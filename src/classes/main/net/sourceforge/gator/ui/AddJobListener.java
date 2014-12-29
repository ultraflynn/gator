package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Job;

public class AddJobListener implements ActionListener
{
    private Estimator estimator;
    private EstimateTabPage parent;
    private DefaultMutableTreeNode rootNode;
    private JTree tree;
    private DefaultTreeModel model;

    public AddJobListener(EstimateTabPage parent)
    {
        this.parent = parent;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void setRootNode(DefaultMutableTreeNode rootNode)
    {
        this.rootNode = rootNode;
    }

    public void setTree(JTree tree, DefaultTreeModel model)
    {
        this.tree = tree;
        this.model = model;
    }

    public void actionPerformed(ActionEvent ev)
    {
        JobDialog dialog = new JobDialog(estimator);
        Job job = dialog.show(parent);

        Estimate estimate = estimator.get();
        estimate.addJob(job);

        DefaultMutableTreeNode node = new DefaultMutableTreeNode(job);
        model.insertNodeInto(node, rootNode, rootNode.getChildCount());

        TreePath nodePath = new TreePath(node.getPath());
        tree.setSelectionPath(nodePath);
        tree.scrollPathToVisible(nodePath);

        EstimateSelectedState state = parent.getState();
        state = state.processEvent(EstimateSelectedState.SELECT_JOB_EVENT, node);
        parent.setState(state);
    }
}
