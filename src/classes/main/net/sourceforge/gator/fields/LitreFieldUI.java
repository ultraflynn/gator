package net.sourceforge.gator.fields;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sourceforge.gator.FieldUI;
import net.sourceforge.gator.Parameters;

public class LitreFieldUI extends FieldUI
{
    private JTextField field;

    public LitreFieldUI()
    {
        super();
        init();
    }

    public void init()
    {
        FlowLayout flayout = new FlowLayout(FlowLayout.LEFT);
        setLayout(flayout);

        JLabel label = new JLabel("Litre:");
        field = new JTextField(5);

        add(label);
        add(field);
    }

    public void clear()
    {
        field.setText("");
    }

    public String getValue()
    {
        return field.getText();
    }

    public void update(Parameters parameters)
    {
    }
}
