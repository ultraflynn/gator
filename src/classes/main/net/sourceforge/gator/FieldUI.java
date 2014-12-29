package net.sourceforge.gator;

import javax.swing.JPanel;

import net.sourceforge.gator.util.Initable;

public abstract class FieldUI extends JPanel implements Initable
{
    private Item item;

    public abstract void clear();
    public abstract String getValue();
    public abstract void update(Parameters parameters);

    public void remove(JPanel fieldPanel)
    {
        clear();
        fieldPanel.remove(this);
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public void change(Parameters parameters)
    {
        update(parameters);
    }
}
