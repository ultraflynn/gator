package net.sourceforge.gator.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Job;

public class JobDialog
{
    private Estimator estimator;

    private JOptionPane optionPane;
    private JPanel mainPanel;

    private JLabel descriptionLabel;
    private JTextField descriptionField;
    private JLabel prefixLabel;
    private JTextField prefixField;
    private JLabel quantityLabel;
    private JTextField quantityField;
    private JLabel unitLabel;
    private JTextField unitField;

    private String windowTitle;

    public JobDialog(Estimator estimator)
    {
        this.estimator = estimator;

        descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(30);
        prefixLabel = new JLabel("Prefix:");
        prefixField = new JTextField(15);
        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(10);
        unitLabel = new JLabel("Unit:");
        unitField = new JTextField(10);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel1.add(descriptionLabel);
        panel1.add(descriptionField);
        panel2.add(prefixLabel);
        panel2.add(prefixField);
        panel3.add(quantityLabel);
        panel3.add(quantityField);
        panel4.add(unitLabel);
        panel4.add(unitField);

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.add(panel4);

        optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
    }

    public Job show(JPanel parent)
    {
        windowTitle = "Create New Job";

        Job job = estimator.createJob();

        return show(parent, job);
    }

    public Job show(JPanel parent, Job job)
    {
        windowTitle = "Edit Job";

        descriptionField.setText(job.getDescription());
        prefixField.setText(job.getQuantityPrefix());
        quantityField.setText(job.getQuantity().toString());
        unitField.setText(job.getQuantityUnit());

        JDialog dialog = optionPane.createDialog(parent, windowTitle);
        dialog.show();

        job.setDescription(descriptionField.getText());
        job.setQuantityPrefix(prefixField.getText());
        job.setQuantity(quantityField.getText());
        job.setQuantityUnit(unitField.getText());

        return job;
    }
}
