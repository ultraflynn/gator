package net.sourceforge.gator.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Job;
import net.sourceforge.gator.Task;

import net.sourceforge.gator.util.ClassUtils;

public class EstimateTreeListener extends MouseAdapter
{
    private JTree tree;
    private JPanel parent;
    private DefaultTreeModel model;
    private Estimator estimator;
    private EstimateTabPage parentPage;

    public EstimateTreeListener(JPanel parent)
    {
        super();
        this.parent = parent;
    }

    public void enableMouseListener()
    {
        tree.addMouseListener(this);
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

    public void setParentPage(EstimateTabPage parentPage)
    {
        this.parentPage = parentPage;
    }

    public void mousePressed(MouseEvent event)
    {
        int selRow = tree.getRowForLocation(event.getX(), event.getY());

        if (selRow != -1) {
            int clickCount = event.getClickCount();

            if (clickCount == 1) {
                int button = event.getButton();

                TreePath path = tree.getPathForLocation(event.getX(), event.getY());
                Object comp = path.getLastPathComponent();

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) comp;
                Object o = node.getUserObject();

                if (ClassUtils.isImplementor(o, "net.sourceforge.gator.Job")) {
                    if (button != MouseEvent.BUTTON1) {
                        Job job = (Job) o;

                        JobDialog dialog = new JobDialog(estimator);
                        job = dialog.show(parent, job);

                        model.reload();

                        TreePath nodePath = new TreePath(node.getPath());
                        tree.scrollPathToVisible(nodePath);
                        tree.setSelectionPath(nodePath);
                    }
                } else if (ClassUtils.isImplementor(o, "net.sourceforge.gator.Task")) {
                    if (button != MouseEvent.BUTTON1) {
                        Task task = (Task) o;

                        TaskDialog dialog = new TaskDialog(estimator);
                        task = dialog.show(parent, task);

                        model.reload();

                        TreePath nodePath = new TreePath(node.getPath());
                        tree.scrollPathToVisible(nodePath);
                        tree.setSelectionPath(nodePath);
                    }
                }

                EstimateSelectedState state = parentPage.getState();

                if (ClassUtils.isImplementor(o, "net.sourceforge.gator.Estimate")) {
                    state = state.processEvent(EstimateSelectedState.SELECT_ESTIMATE_EVENT, node);
                } else if (ClassUtils.isImplementor(o, "net.sourceforge.gator.Job")) {
                    state = state.processEvent(EstimateSelectedState.SELECT_JOB_EVENT, node);
                } else if (ClassUtils.isImplementor(o, "net.sourceforge.gator.Task")) {
                    state = state.processEvent(EstimateSelectedState.SELECT_TASK_EVENT, node);
                } else if (ClassUtils.isImplementor(o, "net.sourceforge.gator.SubTask")) {
                    state = state.processEvent(EstimateSelectedState.SELECT_SUBTASK_EVENT, node);
                }

                parentPage.setState(state);
            }
        }
    }
}
