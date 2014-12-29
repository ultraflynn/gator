package net.sourceforge.gator.helpers;

import java.awt.FlowLayout;

import java.text.DecimalFormat;

import javax.swing.event.DocumentEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sourceforge.gator.HelperUI;
import net.sourceforge.gator.MapParameters;
import net.sourceforge.gator.Parameters;

public class AreaHelperUI extends HelperUI
{
    public static final String AREA = "net.sourceforge.gator.AreaHelperUIArea";

    private JTextField field;

    public AreaHelperUI()
    {
        super();
        init();
    }

    public void init()
    {
        FlowLayout flayout = new FlowLayout(FlowLayout.LEFT);
        setLayout(flayout);

        JLabel label = new JLabel("Area:");
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
        String height = parameters.get(CubicDimensionsHelperUI.HEIGHT);
        String width = parameters.get(CubicDimensionsHelperUI.WIDTH);
        String depth = parameters.get(CubicDimensionsHelperUI.DEPTH);

        try {
            if (height != null && width != null && depth != null) {
                float h = Float.parseFloat(height.trim());
                float w = Float.parseFloat(width.trim());
                float d = Float.parseFloat(depth.trim());

                DecimalFormat df = new DecimalFormat("#0.00");
                String area = df.format(h * w * d);

                field.setText(area);
                parameters.add(AREA, area);
            }
        } catch (NumberFormatException e) {
            field.setText("");
            parameters.remove(AREA);
        }
    }

    protected void valueChangeEvent(DocumentEvent e)
    {
        Parameters parameters = new MapParameters();

        String a = field.getText();

        parameters.add(AREA, a);

        change(parameters);
    }
}
