package net.sourceforge.gator.ui;

import java.awt.FlowLayout;

import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Item;
import net.sourceforge.gator.Estimator;

public class ItemDialog
{
    private Estimator estimator;

    private JOptionPane optionPane;
    private JPanel mainPanel;

    private JLabel nameLabel;
    private JList itemList;

    private String windowTitle;

    public ItemDialog(Estimator estimator)
    {
        this.estimator = estimator;

        nameLabel = new JLabel("Select an item:");

        DefaultListModel model = new DefaultListModel();

        Configuration config = estimator.getConfiguration();
        Set itemNames = config.getItemNames();
        for (Iterator i = itemNames.iterator(); i.hasNext();) {
            String itemName = (String) i.next();
            Item item = config.getItem(itemName);

            model.addElement(item);
        }

        itemList = new JList(model);
        itemList.setVisibleRowCount(15);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        mainPanel = new JPanel();

        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JScrollPane scrollPane = new JScrollPane(itemList);

        mainPanel.add(nameLabel);
        mainPanel.add(scrollPane);

        optionPane = new JOptionPane(mainPanel);
    }

    public Item show(JPanel parent)
    {
        windowTitle = "Select Item";

        JDialog dialog = optionPane.createDialog(parent, windowTitle);
        dialog.show();

        Item item = (Item) itemList.getSelectedValue();

        return item;
    }
}
