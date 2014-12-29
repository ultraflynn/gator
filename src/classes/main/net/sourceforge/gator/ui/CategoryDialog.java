package net.sourceforge.gator.ui;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Estimator;

import net.sourceforge.gator.util.UniqueIdGenerator;

public class CategoryDialog
{
    private Estimator estimator;

    private JOptionPane optionPane;
    private JPanel mainPanel;

    private JLabel descriptionLabel;
    private JTextField descriptionField;

    private String windowTitle;

    public CategoryDialog(Estimator estimator)
    {
        this.estimator = estimator;

        descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(30);

        mainPanel = new JPanel();

        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionField);

        optionPane = new JOptionPane(mainPanel);
    }

    public Category show(JPanel parent)
    {
        windowTitle = "Create New Category";

        Category category = estimator.createCategory();

        String name = UniqueIdGenerator.getTimestamp();
        category.setName(name);

        return show(parent, category);
    }

    public Category show(JPanel parent, Category category)
    {
        windowTitle = "Edit Category";

        descriptionField.setText(category.getDescription());

        JDialog dialog = optionPane.createDialog(parent, windowTitle);
        dialog.show();

        category.setDescription(descriptionField.getText());

        return category;
    }
}
