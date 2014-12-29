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
import net.sourceforge.gator.Task;

public class AddTaskListener implements ActionListener
{
    private EstimateTabPage parent;
    private Estimator estimator;
    private JTree tree;
    private DefaultTreeModel model;

    public AddTaskListener(EstimateTabPage parent)
    {
        this.parent = parent;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void setTree(JTree tree, DefaultTreeModel model)
    {
        this.tree = tree;
        this.model = model;
    }

    public void actionPerformed(ActionEvent ev)
    {
        TaskDialog dialog = new TaskDialog(estimator);
        Task task = dialog.show(parent);

        Estimate estimate = estimator.get();
        TreePath jobNodeTreePath = tree.getSelectionPath();
        DefaultMutableTreeNode jobNode = (DefaultMutableTreeNode) jobNodeTreePath.getLastPathComponent();
        Job job = (Job) jobNode.getUserObject();
        job.addTask(task);

        DefaultMutableTreeNode node = new DefaultMutableTreeNode(task);
        model.insertNodeInto(node, jobNode, jobNode.getChildCount());

        TreePath nodePath = new TreePath(node.getPath());
        tree.setSelectionPath(nodePath);
        tree.scrollPathToVisible(nodePath);

        EstimateSelectedState state = parent.getState();
        state = state.processEvent(EstimateSelectedState.SELECT_TASK_EVENT, node);
        parent.setState(state);
    }
}
