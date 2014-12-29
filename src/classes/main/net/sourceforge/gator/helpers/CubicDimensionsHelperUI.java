package net.sourceforge.gator.helpers;

import java.awt.FlowLayout;

import javax.swing.event.DocumentEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sourceforge.gator.HelperUI;
import net.sourceforge.gator.MapParameters;
import net.sourceforge.gator.Parameters;

public class CubicDimensionsHelperUI extends HelperUI
{
    public static final String HEIGHT = "net.sourceforge.gator.CubicDimensionsHeight";
    public static final String WIDTH = "net.sourceforge.gator.CubicDimensionsWidth";
    public static final String DEPTH = "net.sourceforge.gator.CubicDimensionsDepth";

    private JTextField heightField;
    private JTextField widthField;
    private JTextField depthField;

    public CubicDimensionsHelperUI()
    {
        super();
        init();
    }

    public void init()
    {
        FlowLayout flayout = new FlowLayout(FlowLayout.LEFT);
        setLayout(flayout);

        JLabel heightLabel = new JLabel("Height:");
        heightField = new JTextField(5);
        JLabel widthLabel = new JLabel("Width:");
        widthField = new JTextField(5);
        JLabel depthLabel = new JLabel("Depth:");
        depthField = new JTextField(5);

        heightField.getDocument().addDocumentListener(this);
        widthField.getDocument().addDocumentListener(this);
        depthField.getDocument().addDocumentListener(this);

        add(heightLabel);
        add(heightField);
        add(widthLabel);
        add(widthField);
        add(depthLabel);
        add(depthField);
    }

    public void clear()
    {
        heightField.setText("");
        widthField.setText("");
        depthField.setText("");
    }

    public void update(Parameters parameters)
    {
    }

    protected void valueChangeEvent(DocumentEvent e)
    {
        Parameters parameters = new MapParameters();

        String h = heightField.getText();
        String w = widthField.getText();
        String d = depthField.getText();

        parameters.add(HEIGHT, h);
        parameters.add(WIDTH, w);
        parameters.add(DEPTH, d);

        change(parameters);
    }
}
