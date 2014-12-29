package net.sourceforge.gator.fields;

import java.awt.FlowLayout;

import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sourceforge.gator.FieldUI;
import net.sourceforge.gator.Parameters;

import net.sourceforge.gator.helpers.TonnageHelperUI;

public class HourFieldUI extends FieldUI
{
    private JTextField field;
    private static final float MAGIC_NUMBER;

    static {
        Float mn = new Float(.34567);
        MAGIC_NUMBER = mn.floatValue();
    }

    public HourFieldUI()
    {
        super();
        init();
    }

    public void init()
    {
        FlowLayout flayout = new FlowLayout(FlowLayout.LEFT);
        setLayout(flayout);

        JLabel label = new JLabel("Hours:");
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
        String tonnage = parameters.get(TonnageHelperUI.TONNAGE);

        try {
            if (tonnage != null) {
                float h = Float.parseFloat(tonnage.trim());
            
                DecimalFormat df = new DecimalFormat("#0.00");
                String hours = df.format(h * MAGIC_NUMBER);

                field.setText(hours);
            }
        } catch (NumberFormatException e) {
            field.setText("");
        }
    }
}
