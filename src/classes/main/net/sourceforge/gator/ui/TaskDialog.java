package net.sourceforge.gator.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Task;

public class TaskDialog
{
    private Estimator estimator;

    private JOptionPane optionPane;
    private JPanel mainPanel;

    private JLabel prefixLabel;
    private JTextField prefixField;
    private JLabel idLabel;
    private JTextField idField;
    private JLabel jobDescriptionLabel;
    private JTextField jobDescriptionField;
    private JLabel quantityUnitLabel;
    private JTextField quantityUnitField;
    private JLabel quantityLabel;
    private JTextField quantityField;
    private JLabel profitMarginPercentageLabel;
    private JTextField profitMarginPercentageField;

    private String windowTitle;

    public TaskDialog(Estimator estimator)
    {
        this.estimator = estimator;

        prefixLabel = new JLabel("Prefix:");
        prefixField = new JTextField(5);
        idLabel = new JLabel("Id:");
        idField = new JTextField(3);
        jobDescriptionLabel = new JLabel("Description:");
        jobDescriptionField = new JTextField(50);
        quantityUnitLabel = new JLabel("QuantityUnit:");
        quantityUnitField = new JTextField(10);
        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(12);
        profitMarginPercentageLabel = new JLabel("Profit Margin %age:");
        profitMarginPercentageField = new JTextField(12);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.LEFT));


        panel1.add(prefixLabel);
        panel1.add(prefixField);
        panel2.add(idLabel);
        panel2.add(idField);
        panel3.add(jobDescriptionLabel);
        panel3.add(jobDescriptionField);
        panel4.add(quantityUnitLabel);
        panel4.add(quantityUnitField);
        panel5.add(quantityLabel);
        panel5.add(quantityField);
        panel6.add(profitMarginPercentageLabel);
        panel6.add(profitMarginPercentageField);

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.add(panel4);
        mainPanel.add(panel5);
        mainPanel.add(panel6);

        optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
    }

    public Task show(JPanel parent)
    {
        windowTitle = "Create New Task";

        Task task = estimator.createTask();

        return show(parent, task);
    }

    public Task show(JPanel parent, Task task)
    {
        windowTitle = "Edit Task";

        prefixField.setText(task.getPrefix());
        idField.setText(task.getId().toString());
        jobDescriptionField.setText(task.getJobDescription());
        quantityUnitField.setText(task.getQuantityUnit());
        quantityField.setText(task.getQuantity().toString());
        profitMarginPercentageField.setText(task.getProfitMarginPercentage().toString());

        JDialog dialog = optionPane.createDialog(parent, windowTitle);
        dialog.show();
        /*
         * FIXME
         *
         * This does not seem to return a value. Need to check for return value and
         * do nothing when a "Cancel" is pressed.
         */

        task.setPrefix(prefixField.getText());
        task.setId(Integer.valueOf(idField.getText().trim()));
        task.setJobDescription(jobDescriptionField.getText());
        task.setQuantityUnit(quantityUnitField.getText());
        task.setQuantity(quantityField.getText());
        task.setProfitMarginPercentage(profitMarginPercentageField.getText());

        return task;
    }
}
