package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.Job;
import net.sourceforge.gator.SubTask;
import net.sourceforge.gator.Task;

import net.sourceforge.gator.util.ClassUtils;

public class RemoveEstimateItemListener implements ActionListener
{
    private JPanel parent;
    private JTree estimateTree;
    private DefaultTreeModel estimateTreeModel;

    public RemoveEstimateItemListener(JPanel parent)
    {
        this.parent = parent;
    }

    public void setTree(JTree tree, DefaultTreeModel model)
    {
        this.estimateTree = tree;
        this.estimateTreeModel = model;
    }

    public void actionPerformed(ActionEvent e)
    {
        TreePath taskNodeTreePath = estimateTree.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) taskNodeTreePath.getLastPathComponent();
        Object uo = node.getUserObject();

        if (ClassUtils.isImplementor(uo, "net.sourceforge.gator.Job")) {
            Job job = (Job) uo;
            Estimate p = job.getEstimate();
            p.removeJob(job);
        } else if (ClassUtils.isImplementor(uo, "net.sourceforge.gator.Task")) {
            Task task = (Task) uo;
            Job p = task.getJob();
            p.removeTask(task);
        } else if (ClassUtils.isImplementor(uo, "net.sourceforge.gator.SubTask")) {
            SubTask subTask = (SubTask) uo;
            Task p = subTask.getTask();
            p.removeSubTask(subTask);
        }

        estimateTreeModel.removeNodeFromParent(node);
        estimateTreeModel.reload();
    }
}
