package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.FieldUI;
import net.sourceforge.gator.Item;
import net.sourceforge.gator.Task;
import net.sourceforge.gator.SubTask;

public class AddSubTaskListener implements ActionListener
{
    private EstimateTabPage parent;
    private JTree tree;
    private DefaultTreeModel model;
    private Estimator estimator;
    private FieldUI currentFieldUI;

    public AddSubTaskListener(EstimateTabPage parent)
    {
        this.parent = parent;
    }

    public void setTree(JTree tree, DefaultTreeModel model)
    {
        this.tree = tree;
        this.model = model;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void setCurrentFieldUI(FieldUI currentFieldUI)
    {
        this.currentFieldUI = currentFieldUI;
    }

    public void actionPerformed(ActionEvent ev)
    {
        SubTask subTask = estimator.createSubTask();

        Item item = currentFieldUI.getItem();

        subTask.setType("1"); // FIXME ???
        subTask.setDescription(item.getDescription());
        subTask.setCode("3"); // FIXME ???
        subTask.setQuantityUnit("4"); // FIXME ???
        subTask.setQuantity(currentFieldUI.getValue());
        subTask.setUnitCost(item.getCost().toString());

        Estimate estimate = estimator.get();
        TreePath taskNodeTreePath = tree.getSelectionPath();
        DefaultMutableTreeNode taskNode = (DefaultMutableTreeNode) taskNodeTreePath.getLastPathComponent();
        Task task = (Task) taskNode.getUserObject();
        task.addSubTask(subTask);

        DefaultMutableTreeNode node = new DefaultMutableTreeNode(subTask);
        model.insertNodeInto(node, taskNode, taskNode.getChildCount());

        TreePath nodePath = new TreePath(node.getPath());
        tree.setSelectionPath(nodePath);
        tree.scrollPathToVisible(nodePath);

        TreePath taskNodePath = new TreePath(taskNode.getPath());
        tree.setSelectionPath(taskNodePath);

        EstimateSelectedState state = parent.getState();
        state = state.processEvent(EstimateSelectedState.SELECT_TASK_EVENT, node);
        parent.setState(state);
    }
}
