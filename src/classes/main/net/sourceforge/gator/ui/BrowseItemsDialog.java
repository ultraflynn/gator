package net.sourceforge.gator.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.HelperBundle;
import net.sourceforge.gator.Item;

public class BrowseItemsDialog implements ActionListener, DocumentListener
{
    private static final String WINDOW_TITLE = "Browse Items";

    private Estimator estimator;

    private JOptionPane optionPane;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel detailsPanel;
    private DefaultListModel model;

    private JTextField descriptionField;
    private JTextField costField;
    private JComboBox helperField;
    private JComboBox fieldField;

    private ArrayList helpers;

    private Item currentItem;

    public BrowseItemsDialog(Estimator estimator)
    {
        this.estimator = estimator;

        try {
            initHelpers();

            mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(1, 2));

            rightPanel = new JPanel();
            rightPanel.setLayout(new BorderLayout());

            detailsPanel = new JPanel();
            addDetails(detailsPanel);

            leftPanel = new JPanel();
            addItemList(leftPanel);

            rightPanel.add(detailsPanel, BorderLayout.NORTH);

            mainPanel.add(leftPanel);
            mainPanel.add(rightPanel);

            optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentItem(Item currentItem)
    {
        this.currentItem = currentItem;
    }

    public void show(JPanel parent)
    {
        JDialog dialog = optionPane.createDialog(parent, WINDOW_TITLE);
        dialog.show();
    }

    public void initHelpers()
        throws ConfigurationException
    {
        Configuration config = estimator.getConfiguration();

        helpers = new ArrayList();

        HelperBundle dimensionsBundle = new HelperBundle(config);
        dimensionsBundle.addHelperRef("cubic-area-dimensions");
        dimensionsBundle.addHelperRef("area");
        dimensionsBundle.addHelperRef("tonnage");
        helpers.add(dimensionsBundle);

        HelperBundle areaBundle = new HelperBundle(config);
        areaBundle.addHelperRef("area");
        helpers.add(areaBundle);
    }

    private void addItemList(JPanel panel)
    {
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Items:");

        model = new DefaultListModel();

        List items = new ArrayList();

        Configuration config = estimator.getConfiguration();
        Set itemNames = config.getItemNames();
        for (Iterator i = itemNames.iterator(); i.hasNext();) {
            String itemName = (String) i.next();
            Item item = config.getItem(itemName);

            model.addElement(item);
            items.add(item);
        }

        JList list = new JList(model);
        list.setVisibleRowCount(25);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ItemListListener itemListListener = new ItemListListener();
        itemListListener.setEstimator(estimator);
        itemListListener.setList(list, items);
        itemListListener.setDescription(descriptionField);
        itemListListener.setCost(costField);
        itemListListener.setHelper(helperField);
        itemListListener.setField(fieldField);
        itemListListener.setDialog(this);
        list.addMouseListener(itemListListener);
 
        JScrollPane scrollPane = new JScrollPane(list);

        JButton addItemBtn = new JButton("Add");
        JButton removeItemBtn = new JButton("Remove");

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        AddNewItemListener addNewItemListener = new AddNewItemListener(btnPanel);
        addNewItemListener.setList(list, items);
        addNewItemListener.setEstimator(estimator);
        addItemBtn.addActionListener(addNewItemListener);
        DeleteExistingItemListener deleteExistingItemListener = new DeleteExistingItemListener(btnPanel);
        deleteExistingItemListener.setList(list);
        removeItemBtn.addActionListener(deleteExistingItemListener);

        btnPanel.add(addItemBtn);
        // btnPanel.add(removeItemBtn); // TODO Implement the delete functionality
 
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
    }

    private void addDetails(JPanel panel)
    {
        panel.setLayout(new GridLayout(10, 1));

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        JLabel costLabel = new JLabel("Cost:");
        costField = new JTextField(8);
        JLabel helperLabel = new JLabel("Helper(s):");
        helperField = new JComboBox();
        JLabel fieldLabel = new JLabel("Unit:");
        fieldField = new JComboBox();

        populateHelpers(helperField);
        populateFields(fieldField);

        isEnableListeners(true);

        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(costLabel);
        panel.add(costField);
        panel.add(helperLabel);
        panel.add(helperField);
        panel.add(fieldLabel);
        panel.add(fieldField);

    }

    private void populateHelpers(JComboBox field)
    {
        for (Iterator i = helpers.iterator(); i.hasNext();) {
            HelperBundle bundle = (HelperBundle) i.next();

            field.addItem(bundle);
        }
    }

    private void populateFields(JComboBox field)
    {
        Configuration config = estimator.getConfiguration();

        Set fieldNames = config.getFieldNames();
        for (Iterator i = fieldNames.iterator(); i.hasNext();) {
            String fieldName = (String) i.next();
            Field fieldObj = config.getField(fieldName);

            field.addItem(fieldObj);
        }
    }

    public void changedUpdate(DocumentEvent e) 
    {
        updateDetails(e);
    }

    public void insertUpdate(DocumentEvent e)
    {
        updateDetails(e);
    }

    public void removeUpdate(DocumentEvent e) 
    {
        updateDetails(e);
    }

    public void isEnableListeners(boolean flag)
    {
        if (flag) {
            descriptionField.getDocument().addDocumentListener(this);
            costField.getDocument().addDocumentListener(this);
            helperField.addActionListener(this);
            fieldField.addActionListener(this);
        } else {
            descriptionField.getDocument().removeDocumentListener(this);
            costField.getDocument().removeDocumentListener(this);
            helperField.removeActionListener(this);
            fieldField.removeActionListener(this);
        }
    }

    private void updateDetails(DocumentEvent e)
    {
        if (currentItem != null) {
            currentItem.setDescription(descriptionField.getText());
            currentItem.setCost(costField.getText());
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (currentItem != null) {
            HelperBundle hb = (HelperBundle) helperField.getSelectedItem();
            currentItem.setHelpers(hb);

            Field f = (Field) fieldField.getSelectedItem();
            if (f != null) {
                currentItem.setField(f);
            }
        }
    }
}
