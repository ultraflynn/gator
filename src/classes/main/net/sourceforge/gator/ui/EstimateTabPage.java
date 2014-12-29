package net.sourceforge.gator.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.JScrollPane;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.FieldUI;
import net.sourceforge.gator.Helper;
import net.sourceforge.gator.HelperUI;
import net.sourceforge.gator.Item;
import net.sourceforge.gator.Job;
import net.sourceforge.gator.SubTask;
import net.sourceforge.gator.Task;

import net.sourceforge.gator.util.ClassUtils;

public class EstimateTabPage extends JPanel
{
    private static Log log = LogFactory.getLog("net.sourceforge.gator.ui.EstimateTabPage");

    private Configuration config;
    private FontRegistry fontRegistry;
    private Estimator estimator;
    private MainUI mainUI;

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel topSplitPanel;
    private JPanel dataEntryPanel;
    private JPanel dataEntryTitlePanel;
    private JPanel fieldAndActionPanel;
    private JPanel dataEntryActionsPanel;

    private JPanel helperPanel;
    private JPanel fieldPanel;

    private FieldUI currentFieldUI;
    private HelperUI currentTopHelperUI;

    private JButton browseItemsBtn;
    private JButton addCategoryBtn;
    private JButton addItemBtn;
    private JButton deleteCategoryOrItemBtn;

    private LibrarySelectedState libraryState;
    private EstimateSelectedState estimateState;

    private JButton addJobBtn;
    private JButton addTaskBtn;
    private JButton addSubTaskBtn;
    private JButton removeBtn;

    private JButton saveBtn;
    private JButton printBtn;
    private JButton newEstimateBtn;
    private JButton openEstimateBtn;

    private DefaultMutableTreeNode estimateRootNode;
    private DefaultTreeModel estimateTreeModel;
    private JTree estimateTree;
    private JTree configTree;

    private EstimateTreeListener estimateTreeListener;
    private AddJobListener addJobListener;
    private AddSubTaskListener addSubTaskListener;
    private RemoveEstimateItemListener removeEstimateItemListener;
    private AddTaskListener addTaskListener;

    private BrowseItemsListener browseItemsListener;
    private AddCategoryListener addCategoryListener;
    private AddItemListener addItemListener;
    private DeleteCategoryOrItemListener deleteCategoryOrItemListener;

    private ConfigTreeSelectionListener ctsl;

    public EstimateTabPage()
        throws Exception
    {
        fontRegistry = new FontRegistry();
        fontRegistry.add("NORMAL", "SansSerif", Font.PLAIN, 10);
        fontRegistry.add("H1", "SansSerif", Font.BOLD, 12);
        fontRegistry.add("H6", "SansSerif", Font.ITALIC, 10);
    }

    public EstimateTabPage(FontRegistry fontRegistry)
        throws Exception
    {
        this.fontRegistry = fontRegistry;
    }

    public void setMainUI(MainUI mainUI)
    {
        this.mainUI = mainUI;
    }

    public void reload()
    {
        loadEstimateDetails();
    }

    public void setConfiguration(Configuration config)
    {
        this.config = config;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void addToTab(JTabbedPane tabpane)
    {
        tabpane.addTab("Estimate", this);
    }

    public void init()
        throws Exception
    {
        setLayout(new GridLayout(2, 1));
        Font normalFont = fontRegistry.get("NORMAL");
        setFont(normalFont);

        topPanel = new JPanel();
        bottomPanel = new JPanel();
        topSplitPanel = new JPanel();
        dataEntryPanel = new JPanel();

        dataEntryTitlePanel = new JPanel();
        helperPanel = new JPanel();
        fieldAndActionPanel = new JPanel();

        fieldPanel = new JPanel();
        dataEntryActionsPanel = new JPanel();

        dataEntryActionsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        addDataEntryActionsPanel(dataEntryActionsPanel);

        // FIXME This is centering
        fieldAndActionPanel.setLayout(new BorderLayout());
        fieldAndActionPanel.add(fieldPanel, BorderLayout.CENTER);
        fieldAndActionPanel.add(dataEntryActionsPanel, BorderLayout.SOUTH);

        dataEntryPanel.setLayout(new BorderLayout());
        dataEntryPanel.add(dataEntryTitlePanel, BorderLayout.NORTH);
        dataEntryPanel.add(helperPanel, BorderLayout.CENTER);
        dataEntryPanel.add(fieldAndActionPanel, BorderLayout.SOUTH);

        topPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.setLayout(new BorderLayout());

        addConfigTree(topPanel);
        topPanel.add(dataEntryPanel);

        addEstimateDetails(bottomPanel);

        addSubTaskListener.setTree(estimateTree, estimateTreeModel);
        removeEstimateItemListener.setTree(estimateTree, estimateTreeModel);

        add(topPanel);
        add(bottomPanel);

        libraryState = LibrarySelectedState.start(addCategoryBtn, addItemBtn, deleteCategoryOrItemBtn);
    }

    private void addDataEntryActionsPanel(JPanel panel)
        throws Exception
    {
        addSubTaskBtn = new JButton("Add");
        removeBtn = new JButton("Remove");

        addSubTaskListener = new AddSubTaskListener(this);
        addSubTaskListener.setEstimator(estimator);
        addSubTaskBtn.addActionListener(addSubTaskListener);

        removeEstimateItemListener = new RemoveEstimateItemListener(this);
        removeBtn.addActionListener(removeEstimateItemListener);

        panel.add(addSubTaskBtn);
        panel.add(removeBtn);
    }

    private void addEstimateDetails(JPanel panel)
        throws Exception
    {
        estimateTree = new JTree();
        TreeSelectionModel tsm = estimateTree.getSelectionModel();
        tsm.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(estimateTree);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JPanel rightButtons = new JPanel();
        JPanel leftButtons = new JPanel();

        rightButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        leftButtons.setLayout(new FlowLayout(FlowLayout.LEFT));

        addJobBtn = new JButton("New Job");
        addTaskBtn = new JButton("New Task");

        estimateState = EstimateSelectedState.start(addJobBtn, addTaskBtn, addSubTaskBtn, removeBtn, estimateRootNode);

        addJobListener = new AddJobListener(this);
        addJobListener.setEstimator(estimator);
        addJobListener.setTree(estimateTree, estimateTreeModel);
        addJobListener.setRootNode(estimateRootNode);
        addJobBtn.addActionListener(addJobListener);
        addTaskListener = new AddTaskListener(this);
        addTaskListener.setEstimator(estimator);
        addTaskListener.setTree(estimateTree, estimateTreeModel);
        addTaskBtn.addActionListener(addTaskListener);

        leftButtons.add(addJobBtn);
        leftButtons.add(addTaskBtn);

        saveBtn = new JButton("Save");
        printBtn = new JButton("Print");
        newEstimateBtn = new JButton("New Estimate");
        openEstimateBtn = new JButton("Open Estimate");

        SaveEstimateListener saveEstimateListener = new SaveEstimateListener(this);
        saveEstimateListener.setEstimator(estimator);
        saveBtn.addActionListener(saveEstimateListener);

        NewEstimateListener newEstimateListener = new NewEstimateListener(this);
        newEstimateListener.setEstimator(estimator);
        newEstimateListener.setMainUI(mainUI);
        newEstimateBtn.addActionListener(newEstimateListener);

        OpenEstimateListener openEstimateListener = new OpenEstimateListener(this);
        openEstimateListener.setEstimator(estimator);
        openEstimateListener.setMainUI(mainUI);
        openEstimateBtn.addActionListener(openEstimateListener);

        estimateTreeListener = new EstimateTreeListener(panel);
        estimateTreeListener.setTree(estimateTree, estimateTreeModel);
        estimateTreeListener.setParentPage(this);
        estimateTreeListener.enableMouseListener();

        rightButtons.add(saveBtn);
        rightButtons.add(printBtn);
        rightButtons.add(newEstimateBtn);
        rightButtons.add(openEstimateBtn);

        buttonPanel.add(leftButtons);
        buttonPanel.add(rightButtons);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadEstimateDetails();
    }

    private void addConfigTree(JPanel panel)
        throws Exception
    {
        JPanel main = new JPanel();
        BorderLayout layout = new BorderLayout();
        main.setLayout(layout);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        browseItemsBtn = new JButton("Browse Items");
        addCategoryBtn = new JButton("Add Category");
        addItemBtn = new JButton("Add Item");
        deleteCategoryOrItemBtn = new JButton("Delete");

        browseItemsListener = new BrowseItemsListener(this);
        browseItemsListener.setEstimator(estimator);
        browseItemsBtn.addActionListener(browseItemsListener);
        addCategoryListener = new AddCategoryListener(this);
        addCategoryListener.setEstimator(estimator);
        addCategoryListener.setEstimateTabPage(this);
        addCategoryBtn.addActionListener(addCategoryListener);
        addItemListener = new AddItemListener(this);
        addItemListener.setEstimator(estimator);
        addItemListener.setEstimateTabPage(this);
        addItemBtn.addActionListener(addItemListener);
        deleteCategoryOrItemListener = new DeleteCategoryOrItemListener(this);
        deleteCategoryOrItemListener.setEstimator(estimator);
        deleteCategoryOrItemListener.setEstimateTabPage(this);
        deleteCategoryOrItemBtn.addActionListener(deleteCategoryOrItemListener);

        buttons.add(browseItemsBtn);
        buttons.add(addCategoryBtn);
        buttons.add(addItemBtn);
        buttons.add(deleteCategoryOrItemBtn);

        configTree = new JTree();
        loadConfigTree();

        JScrollPane scrollPane = new JScrollPane(configTree);
        main.add(scrollPane, BorderLayout.CENTER);
        main.add(buttons, BorderLayout.NORTH);

        panel.add(main);
    }

    private class ConfigTreeSelectionListener implements TreeSelectionListener
    {
        public void valueChanged(TreeSelectionEvent event)
        {
            try {
                TreePath path = event.getNewLeadSelectionPath();
                Object comp = path.getLastPathComponent();

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) comp;
                Object uo = node.getUserObject();

                if (ClassUtils.isImplementor(uo, "net.sourceforge.gator.Category")) {
                    Category category = (Category) uo;

                    log.trace("Category selected '" + category + "'");

                    libraryState = libraryState.processEvent(LibrarySelectedState.SELECT_CATEGORY_EVENT);
                } else if (ClassUtils.isImplementor(uo, "net.sourceforge.gator.Item")) {
                    Item item = (Item) uo;

                    log.trace("Item selected '" + item + "'");

                    libraryState = libraryState.processEvent(LibrarySelectedState.SELECT_ITEM_EVENT);

                    changeHelpersAndField(item);
                } else {
                    log.trace("Root Library selected");

                    libraryState = libraryState.processEvent(LibrarySelectedState.SELECT_LIBRARY_EVENT);
                }
            } catch (Exception e) {
                log.error("Could not process library tree selection", e);
            }
        }
    }

    public void loadConfigTree()
        throws Exception
    {
        if (ctsl != null) {
            configTree.removeTreeSelectionListener(ctsl);
        }

        List categories = config.getCategories();
        String rootCategoryName = config.getRootCategoryName();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootCategoryName);
        addCategories(root, categories);

        DefaultTreeModel dtm = new DefaultTreeModel(root);
        configTree.setModel(dtm);
        TreeSelectionModel tsm = configTree.getSelectionModel();
        tsm.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        Font normalFont = fontRegistry.get("NORMAL");
        configTree.setFont(normalFont);

        ctsl = new ConfigTreeSelectionListener();
        configTree.addTreeSelectionListener(ctsl);

        addCategoryListener.setTree(configTree);
        addCategoryListener.setEstimator(estimator);
        addItemListener.setTree(configTree);
        deleteCategoryOrItemListener.setTree(configTree);
    }

    private void changeHelpersAndField(Item item)
        throws Exception
    {
        if (currentTopHelperUI != null) {
            currentTopHelperUI.remove(fieldPanel, helperPanel);
        }
        
        currentFieldUI = addField(fieldPanel, item);
        addSubTaskListener.setCurrentFieldUI(currentFieldUI);
        currentTopHelperUI = addHelpers(helperPanel, item, currentFieldUI);

        dataEntryPanel.validate();
    }

    private void addCategories(DefaultMutableTreeNode parent, List categories)
    {
        for (Iterator i = categories.iterator(); i.hasNext();) {
            Category category = (Category) i.next();

            String description = category.getDescription();
            List itemNames = category.getItemNames();

            DefaultMutableTreeNode node = new DefaultMutableTreeNode(category);
            parent.add(node);

            List subCategories = category.getSubCategories();
            if (subCategories.size() > 0) {
                addCategories(node, subCategories);
            }

            addItems(node, itemNames);
        }
    }

    private void populateEstimateTree(DefaultMutableTreeNode estimateNode, Estimate estimate)
    {
        List jobs = estimate.getJobs();
        for (Iterator i = jobs.iterator(); i.hasNext();) {
            Job job = (Job) i.next();
            DefaultMutableTreeNode jobNode = insertNode(estimateNode, job);

            List tasks = job.getTasks();
            for (Iterator j = tasks.iterator(); j.hasNext();) {
                Task task = (Task) j.next();
                DefaultMutableTreeNode taskNode = insertNode(jobNode, task);

                List subTasks = task.getSubTasks();
                for (Iterator k = subTasks.iterator(); k.hasNext();) {
                    SubTask subTask = (SubTask) k.next();
                    insertNode(taskNode, subTask);
                }
            }
        }
    }

    private DefaultMutableTreeNode insertNode(DefaultMutableTreeNode parent, Object o)
    {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(o);
        estimateTreeModel.insertNodeInto(node, parent, parent.getChildCount());

        return node;
    }

    public void addItems(DefaultMutableTreeNode parent, List itemNames)
    {
        for (Iterator i = itemNames.iterator(); i.hasNext();) {
            String itemName = (String) i.next();
            Item item = config.getItem(itemName);

            String description = item.getDescription();

            DefaultMutableTreeNode node = new DefaultMutableTreeNode(description);
            node.setUserObject(item);
            parent.add(node);
        }
    }

    public EstimateSelectedState getState()
    {
        return estimateState;
    }

    public void setState(EstimateSelectedState estimateState)
    {
        this.estimateState = estimateState;
    }

    private HelperUI addHelpers(JPanel panel, Item item, FieldUI fieldUI)
        throws Exception
    {
        HelperUI first = null;
        HelperUI last = null;
        HelperUI previous = null;

        List helpers = item.getHelpers();

        int size = helpers.size();
        panel.setLayout(new GridLayout(1, size)); // FIXME This layout is not working

        for (Iterator i = helpers.iterator(); i.hasNext();) {
            Helper helper = (Helper) i.next();

            HelperUI helperUI = helper.getUIClass();
            last = helperUI;

            if (previous == null) {
                first = helperUI;
            } else {
                previous.setNextHelperUI(helperUI);
            }

            panel.add(helperUI);

            previous = helperUI;
        }

        last.setFieldUI(fieldUI);

        return first;
    }

    private FieldUI addField(JPanel panel, Item item)
        throws Exception
    {
        Field field = item.getField();
        FieldUI fieldUI = field.getUIClass();

        fieldUI.setItem(item);

        panel.add(fieldUI);

        return fieldUI;
    }

    private void loadEstimateDetails()
    {
        Estimate estimate = estimator.get();

        estimateRootNode = new DefaultMutableTreeNode(estimate);
        estimateTreeModel = new DefaultTreeModel(estimateRootNode);
        populateEstimateTree(estimateRootNode, estimate);

        estimateTreeListener.setTree(estimateTree, estimateTreeModel);
        addJobListener.setRootNode(estimateRootNode);
        addJobListener.setTree(estimateTree, estimateTreeModel);
        addTaskListener.setTree(estimateTree, estimateTreeModel);

        estimateTree.setModel(estimateTreeModel);

        TreePath nodePath = new TreePath(estimateRootNode.getPath());
        estimateTree.setSelectionPath(nodePath);
        estimateTree.scrollPathToVisible(nodePath);
    }
}
