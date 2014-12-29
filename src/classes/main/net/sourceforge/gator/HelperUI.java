package net.sourceforge.gator;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JPanel;

import net.sourceforge.gator.util.Initable;

public abstract class HelperUI extends JPanel implements Initable, DocumentListener
{
    private HelperUI nextHelperUI;
    private FieldUI fieldUI;

    public abstract void clear();
    public abstract void update(Parameters parameters);

    protected abstract void valueChangeEvent(DocumentEvent e);

    public void setNextHelperUI(HelperUI nextHelperUI)
    {
        this.nextHelperUI = nextHelperUI;
    }

    public void setFieldUI(FieldUI fieldUI)
    {
        this.fieldUI = fieldUI;
    }

    public void remove(JPanel fieldPanel, JPanel helperPanel)
    {
        if (nextHelperUI != null) {
            nextHelperUI.remove(fieldPanel, helperPanel);
        }

        if (fieldUI != null) {
            fieldUI.remove(fieldPanel);
        }

        clear();
        helperPanel.remove(this);
    }

    public void change(Parameters parameters)
    {
        update(parameters);

        if (nextHelperUI != null) {
            nextHelperUI.change(parameters);
        }

        if (fieldUI != null) {
            fieldUI.change(parameters);
        }
    }

    public void changedUpdate(DocumentEvent e) 
    {
    }

    public void insertUpdate(DocumentEvent e)
    {
        valueChangeEvent(e);
    }

    public void removeUpdate(DocumentEvent e) 
    {
        valueChangeEvent(e);
    }
}
