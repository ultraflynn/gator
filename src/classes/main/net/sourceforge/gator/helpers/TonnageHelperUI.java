package net.sourceforge.gator.helpers;

import java.awt.FlowLayout;

import java.text.DecimalFormat;

import javax.swing.event.DocumentEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sourceforge.gator.HelperUI;
import net.sourceforge.gator.MapParameters;
import net.sourceforge.gator.Parameters;

public class TonnageHelperUI extends HelperUI
{
    public static final String TONNAGE = "net.sourceforge.gator.TonnageHelperTonnage";

    private static final float MAGIC_NUMBER;

    static {
        Float mn = new Float(1.1172);
        MAGIC_NUMBER = mn.floatValue();
    }

    private JTextField field;

    public TonnageHelperUI()
    {
        super();
        init();
    }

    public void init()
    {
        FlowLayout flayout = new FlowLayout(FlowLayout.LEFT);
        setLayout(flayout);

        JLabel label = new JLabel("Tonnage:");
        field = new JTextField(5);

        field.getDocument().addDocumentListener(this);

        add(label);
        add(field);
    }

    public void clear()
    {
        field.setText("");
    }

    public void update(Parameters parameters)
    {
        String area = parameters.get(AreaHelperUI.AREA);

        try {
            if (area != null) {
                float a = Float.parseFloat(area.trim());

                DecimalFormat df = new DecimalFormat("#0.00");
                String tonnage = df.format(a * MAGIC_NUMBER);

                field.setText(tonnage);
                parameters.add(TONNAGE, tonnage);
            }
        } catch (NumberFormatException e) {
            field.setText("");
            parameters.remove(TONNAGE);
        }
    }

    protected void valueChangeEvent(DocumentEvent e)
    {
        Parameters parameters = new MapParameters();

        String a = field.getText();

        parameters.add(TONNAGE, a);

        change(parameters);
    }
}
