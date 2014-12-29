package net.sourceforge.gator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JPanel;

public class DeleteExistingItemListener implements ActionListener
{
    private JPanel parent;
    private JList list;

    public DeleteExistingItemListener(JPanel parent)
    {
        this.parent = parent;
    }

    public void setList(JList list)
    {
        this.list = list;
    }

    public void actionPerformed(ActionEvent ev)
    {
        System.out.println("ok1");
    }
}
