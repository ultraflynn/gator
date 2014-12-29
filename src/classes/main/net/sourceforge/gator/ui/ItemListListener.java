package net.sourceforge.gator.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;

import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.Helper;
import net.sourceforge.gator.HelperBundle;
import net.sourceforge.gator.Item;

public class ItemListListener extends MouseAdapter
{
    private Estimator estimator;
    private JList list;
    private List items;
    private JTextField description;
    private JTextField cost;
    private JComboBox helper;
    private JComboBox field;
    private BrowseItemsDialog dialog;

    public void setList(JList list, List items)
    {
        this.list = list;
        this.items = items;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void setDescription(JTextField description)
    {
        this.description = description;
    }

    public void setCost(JTextField cost)
    {
        this.cost = cost;
    }

    public void setHelper(JComboBox helper)
    {
        this.helper = helper;
    }

    public void setField(JComboBox field)
    {
        this.field = field;
    }

    public void setDialog(BrowseItemsDialog dialog)
    {
        this.dialog = dialog;
    }

    public void mouseClicked(MouseEvent e)
    {
        dialog.isEnableListeners(false);

        int i = list.getSelectedIndex();
        Item item = (Item) items.get(i);

        dialog.setCurrentItem(item);

        populateDescription(item);
        populateCost(item);
        populateHelper(item);
        populateField(item);

        dialog.isEnableListeners(true);
    }

    private void populateDescription(Item item)
    {
        String descriptionValue = item.getDescription();
        description.setText(descriptionValue);
    }

    private void populateCost(Item item)
    {
        String costValue = item.getCost().toString();
        cost.setText(costValue);
    }

    private void populateHelper(Item item)
    {
        List helpers = item.getHelpers();

        Configuration config = estimator.getConfiguration();
        HelperBundle bundle = new HelperBundle(config);

        for (Iterator i = helpers.iterator(); i.hasNext();) {
            Helper helperObj = (Helper) i.next();

            bundle.addHelper(helperObj);
        }

        helper.setSelectedIndex(-1);

        int cnt = helper.getItemCount();
        for (int i = 0; i < cnt; i++) {
            HelperBundle helperBundle = (HelperBundle) helper.getItemAt(i);

            if (helperBundle.equals(bundle)) {
                helper.setSelectedIndex(i);
                break;
            }
        }
    }

    private void populateField(Item item)
    {
        Field itemField = item.getField();

        field.setSelectedIndex(-1);

        if (itemField != null) {
            String itemFieldName = itemField.getName();

            if (itemFieldName != null) {
                int cnt = field.getItemCount();
                for (int i = 0; i < cnt; i++) {
                    Field fieldObj = (Field) field.getItemAt(i);

                    if (fieldObj.getName().equals(itemField.getName())) {
                        field.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }
}
