package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Item;

import net.sourceforge.gator.util.UniqueIdGenerator;

public class AddNewItemListener implements ActionListener
{
    private static final String NEW_ITEM_DESCRIPTION = "New Item";

    private JPanel parent;
    private JList list;
    private Estimator estimator;
    private List itemList;

    public AddNewItemListener(JPanel parent)
    {
        this.parent = parent;
    }

    public void setList(JList list, List itemList)
    {
        this.list = list;
        this.itemList = itemList;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void actionPerformed(ActionEvent ev)
    {
        Item item = estimator.createItem();

        String name = UniqueIdGenerator.getTimestamp();

        item.setName(name);
        item.setDescription(NEW_ITEM_DESCRIPTION);
        
        if (!(itemExists(item))) {
            DefaultListModel model = (DefaultListModel) list.getModel();
            Configuration config = estimator.getConfiguration();

            config.addItem(item);
            model.addElement(item);
            itemList.add(item);
        }
    }

    private boolean itemExists(Item checkItem)
    {
        for (Iterator i = itemList.iterator(); i.hasNext();) {
            Item item = (Item) i.next();

            if (item.getName().equals(checkItem.getName())) {
                return true;
            }
        }

        return false;
    }
}
