package net.sourceforge.gator.ui;

import javax.swing.JButton;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EstimateSelectedState
{
    public static final int SELECT_ESTIMATE_EVENT = 1;
    public static final int SELECT_JOB_EVENT = 2;
    public static final int SELECT_TASK_EVENT = 3;
    public static final int SELECT_SUBTASK_EVENT = 4;

    private static Log log = LogFactory.getLog("net.sourceforge.gator.ui.EstimateSelectedState");

    private static boolean initialised;

    private static EstimateSelected estimateSelected;
    private static JobSelected jobSelected;
    private static TaskSelected taskSelected;
    private static SubTaskSelected subTaskSelected;

    private JButton addJobBtn;
    private JButton addTaskBtn;
    private JButton addSubTaskBtn;
    private JButton removeBtn;

    private EstimateSelectedState()
    {
        if (!initialised) {
            initialised = true;
            estimateSelected = new EstimateSelected();
            jobSelected = new JobSelected();
            taskSelected = new TaskSelected();
            subTaskSelected = new SubTaskSelected();
        }
    }

    public static EstimateSelectedState start(JButton b1, JButton b2, JButton b3, JButton b4, DefaultMutableTreeNode n)
    {
        EstimateSelectedState initState = new EstimateSelectedState();

        initState.addJobBtn = b1;
        initState.addTaskBtn = b2;
        initState.addSubTaskBtn = b3;
        initState.removeBtn = b4;

        initState.estimateSelected.enter(n);

        return initState.estimateSelected;
    }

    protected EstimateSelectedState nextState(int event)
    {
        throw new IllegalAccessError();
    }

    public final EstimateSelectedState processEvent(int event, DefaultMutableTreeNode node)
    {
        log.trace("Processing event: " + event);

        EstimateSelectedState myNextState = nextState(event);

        log.trace("Current state: " + this.getClass().getName());
        log.trace("Next state: " + myNextState.getClass().getName());

        if (this != myNextState) {
            myNextState.enter(node);
        }

        return myNextState;
    }

    protected void enter(DefaultMutableTreeNode node)
    {
    }

    private class EstimateSelected extends EstimateSelectedState
    {
        protected EstimateSelectedState nextState(int event)
        {
            log.trace("EstimateSelected nextState fired");

            switch (event) {
                case SELECT_ESTIMATE_EVENT:
                    return this;
                case SELECT_JOB_EVENT:
                    return jobSelected;
                case SELECT_TASK_EVENT:
                    return taskSelected;
                case SELECT_SUBTASK_EVENT:
                    return subTaskSelected;
                default:
                    String msg = "unexpected event " + event;
                    throw new IllegalArgumentException(msg);
            }
        }

        protected void enter(DefaultMutableTreeNode node)
        {
            log.trace("EstimateSelected state entered");

            addJobBtn.setEnabled(true);
            addTaskBtn.setEnabled(false);
            addSubTaskBtn.setEnabled(false);
            removeBtn.setEnabled(false);
        }
    }

    private class JobSelected extends EstimateSelectedState
    {
        protected EstimateSelectedState nextState(int event)
        {
            log.trace("JobSelected nextState fired");

            switch (event) {
                case SELECT_ESTIMATE_EVENT:
                    return estimateSelected;
                case SELECT_JOB_EVENT:
                    return this;
                case SELECT_TASK_EVENT:
                    return taskSelected;
                case SELECT_SUBTASK_EVENT:
                    return subTaskSelected;
                default:
                    String msg = "unexpected event " + event;
                    throw new IllegalArgumentException(msg);
            }
        }

        protected void enter(DefaultMutableTreeNode node)
        {
            log.trace("JobSelected state entered");

            addJobBtn.setEnabled(false);
            addTaskBtn.setEnabled(true);
            addSubTaskBtn.setEnabled(false);
            removeBtn.setEnabled(node.getChildCount() == 0);
        }
    }

    private class TaskSelected extends EstimateSelectedState
    {
        protected EstimateSelectedState nextState(int event)
        {
            log.trace("TaskSelected nextState fired");

            switch (event) {
                case SELECT_ESTIMATE_EVENT:
                    return estimateSelected;
                case SELECT_JOB_EVENT:
                    return jobSelected;
                case SELECT_TASK_EVENT:
                    return this;
                case SELECT_SUBTASK_EVENT:
                    return subTaskSelected;
                default:
                    String msg = "unexpected event " + event;
                    throw new IllegalArgumentException(msg);
            }
        }

        protected void enter(DefaultMutableTreeNode node)
        {
            log.trace("TaskSelected state entered");

            addJobBtn.setEnabled(false);
            addTaskBtn.setEnabled(false);
            addSubTaskBtn.setEnabled(true);
            removeBtn.setEnabled(node.getChildCount() == 0);
        }
    }

    private class SubTaskSelected extends EstimateSelectedState
    {
        protected EstimateSelectedState nextState(int event)
        {
            log.trace("SubTaskSelected nextState fired");

            switch (event) {
                case SELECT_ESTIMATE_EVENT:
                    return estimateSelected;
                case SELECT_JOB_EVENT:
                    return jobSelected;
                case SELECT_TASK_EVENT:
                    return taskSelected;
                case SELECT_SUBTASK_EVENT:
                    return this;
                default:
                    String msg = "unexpected event " + event;
                    throw new IllegalArgumentException(msg);
            }
        }

        protected void enter(DefaultMutableTreeNode node)
        {
            log.trace("SubTaskSelected state entered");

            addJobBtn.setEnabled(false);
            addTaskBtn.setEnabled(false);
            addSubTaskBtn.setEnabled(false);
            removeBtn.setEnabled(node.getChildCount() == 0);
        }
    }
}
