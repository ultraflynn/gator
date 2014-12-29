package net.sourceforge.gator.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.EtchedBorder;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.Estimator;

public class CustomerDetailsTabPage extends JPanel implements DocumentListener
{
    private Configuration config;
    private FontRegistry fontRegistry;
    private Estimator estimator;
    private MainUI mainUI;

    private JTextField estimateNumberField;
    private JTextField internalReferenceField;
    private JTextField customerNameField;
    private JTextField contactNameField;
    private JTextArea customerAddressField;
    private JTextField homePhoneField;
    private JTextField faxField;
    private JTextField workPhoneField;
    private JTextField mobileField;
    private JTextField notesField;
    private JTextField jobCustomerNameField;
    private JTextField siteContactField;
    private JTextField jobPhoneField;
    private JTextArea jobAddressField;
    private JTextArea worksRequiredField;
    private JTextArea jobNotesField;
    private JTextArea furtherInstructionsField;

    private JButton asAboveBtn;

    public CustomerDetailsTabPage()
        throws Exception
    {
        fontRegistry = new FontRegistry();
        fontRegistry.add("NORMAL", "Dialog", Font.PLAIN, 11);
        fontRegistry.add("BOLD", "Dialog", Font.BOLD, 11);
        fontRegistry.add("H1", "Dialog", Font.BOLD, 14);
    }

    public CustomerDetailsTabPage(FontRegistry fontRegistry)
        throws Exception
    {
        this.fontRegistry = fontRegistry;
    }

    public void setMainUI(MainUI mainUI)
    {
        this.mainUI = mainUI;
    }

    public void reload()
    {
        loadEstimateDetails();
    }

    public void setConfiguration(Configuration config)
    {
        this.config = config;
    }

    public void setEstimator(Estimator estimator)
    {
        this.estimator = estimator;
    }

    public void addToTab(JTabbedPane tabpane)
    {
        tabpane.addTab("Customer", this);
    }

    public void init()
        throws Exception
    {
        setLayout(new BorderLayout());
        JPanel subpanel = new JPanel(new BorderLayout());
        JPanel subpanel1 = new JPanel(new BorderLayout());
        JPanel subpanel2 = new JPanel(new BorderLayout());
        JPanel subpanel3 = new JPanel(new BorderLayout());

        JPanel estimateSummary = getEstimateSummary();
        JPanel customerDetailsPanel = getCustomerDetailsPanel();
        JPanel jobDetailsPanel = getJobDetailsPanel();
        JPanel jobSummaryPanel = getJobSummaryPanel();

        loadEstimateDetails();

        subpanel3.add(jobSummaryPanel, BorderLayout.NORTH);
        subpanel2.add(jobDetailsPanel, BorderLayout.NORTH);
        subpanel2.add(subpanel3, BorderLayout.CENTER);
        subpanel1.add(customerDetailsPanel, BorderLayout.NORTH);
        subpanel1.add(subpanel2, BorderLayout.CENTER);
        subpanel.add(estimateSummary, BorderLayout.NORTH);
        subpanel.add(subpanel1, BorderLayout.CENTER);

        add(subpanel, BorderLayout.CENTER);
    }

    private JPanel getEstimateSummary()
        throws Exception
    {
        JPanel panel = new JPanel(new BorderLayout());

        Font bold = fontRegistry.get("BOLD");

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Estimate Number: "));
        estimateNumberField = new JTextField(12);
        estimateNumberField.setFont(bold);
        estimateNumberField.setEnabled(false);
        p1.add(estimateNumberField);
        panel.add(p1, BorderLayout.EAST);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Our Ref: "));
        internalReferenceField = new JTextField(8);
        internalReferenceField.setFont(bold);
        p2.add(internalReferenceField);
        panel.add(p2, BorderLayout.WEST);

        return panel;
    }

    private JPanel getCustomerDetailsPanel()
        throws Exception
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Font h1 = fontRegistry.get("H1");
        JLabel l1 = new JLabel("Customer Details");
        l1.setFont(h1);
        p1.add(l1);
        panel.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField(20);
        p2.add(customerNameField);
        p2.add(new JLabel("Contact:"));
        contactNameField = new JTextField(30);
        p2.add(contactNameField);
        panel.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Address:"));
        customerAddressField = new JTextArea(5, 40);
        Font normal = fontRegistry.get("NORMAL");
        customerAddressField.setFont(normal);
        p3.add(new JScrollPane(customerAddressField));
        panel.add(p3);

        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(new JLabel("Home Phone:"));
        homePhoneField = new JTextField(12);
        p4.add(homePhoneField);
        p4.add(new JLabel("Fax:"));
        faxField = new JTextField(12);
        p4.add(faxField);
        p4.add(new JLabel("Work Phone:"));
        workPhoneField = new JTextField(12);
        p4.add(workPhoneField);
        p4.add(new JLabel("Mobile:"));
        mobileField = new JTextField(12);
        p4.add(mobileField);
        panel.add(p4);

        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Notes:"));
        notesField = new JTextField(70);
        p5.add(notesField);
        panel.add(p5);

        return panel;
    }

    private JPanel getJobDetailsPanel()
        throws Exception
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Font h1 = fontRegistry.get("H1");
        JLabel l1 = new JLabel("Job Details");
        l1.setFont(h1);
        p1.add(l1);
        panel.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Customer Name:"));
        jobCustomerNameField = new JTextField(20);
        p2.add(jobCustomerNameField);
        p2.add(new JLabel("Site Contact:"));
        siteContactField = new JTextField(30);
        p2.add(siteContactField);
        panel.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Job Phone:"));
        jobPhoneField = new JTextField(20);
        p3.add(jobPhoneField);
        panel.add(p3);

        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(new JLabel("Site Address:"));
        jobAddressField = new JTextArea(5, 40);
        Font normal = fontRegistry.get("NORMAL");
        jobAddressField.setFont(normal);
        p4.add(new JScrollPane(jobAddressField));
        asAboveBtn = new JButton("As Above");
        asAboveBtn.addActionListener(new AsAboveListener(this));
        p4.add(asAboveBtn);
        panel.add(p4);

        return panel;
    }

    private JPanel getJobSummaryPanel()
        throws Exception
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Font h1 = fontRegistry.get("H1");
        JLabel l1 = new JLabel("Job Summary");
        l1.setFont(h1);
        p1.add(l1);
        panel.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Works Required:"));
        worksRequiredField = new JTextArea(6, 60);
        Font normal = fontRegistry.get("NORMAL");
        worksRequiredField.setFont(normal);
        p2.add(new JScrollPane(worksRequiredField));
        panel.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Job Notes:"));
        jobNotesField = new JTextArea(4, 30);
        jobNotesField.setFont(normal);
        p3.add(new JScrollPane(jobNotesField));
        p3.add(new JLabel("Further Instructions:"));
        furtherInstructionsField = new JTextArea(4, 30);
        furtherInstructionsField.setFont(normal);
        p3.add(new JScrollPane(furtherInstructionsField));
        panel.add(p3);

        return panel;
    }

    private void isEnableListeners(boolean flag)
    {
        if (flag) {
            internalReferenceField.getDocument().addDocumentListener(this);
            customerNameField.getDocument().addDocumentListener(this);
            contactNameField.getDocument().addDocumentListener(this);
            customerAddressField.getDocument().addDocumentListener(this);
            homePhoneField.getDocument().addDocumentListener(this);
            faxField.getDocument().addDocumentListener(this);
            workPhoneField.getDocument().addDocumentListener(this);
            mobileField.getDocument().addDocumentListener(this);
            notesField.getDocument().addDocumentListener(this);
            jobCustomerNameField.getDocument().addDocumentListener(this);
            siteContactField.getDocument().addDocumentListener(this);
            jobPhoneField.getDocument().addDocumentListener(this);
            jobAddressField.getDocument().addDocumentListener(this);
            worksRequiredField.getDocument().addDocumentListener(this);
            jobNotesField.getDocument().addDocumentListener(this);
            furtherInstructionsField.getDocument().addDocumentListener(this);
        } else {
            internalReferenceField.getDocument().removeDocumentListener(this);
            customerNameField.getDocument().removeDocumentListener(this);
            contactNameField.getDocument().removeDocumentListener(this);
            customerAddressField.getDocument().removeDocumentListener(this);
            homePhoneField.getDocument().removeDocumentListener(this);
            faxField.getDocument().removeDocumentListener(this);
            workPhoneField.getDocument().removeDocumentListener(this);
            mobileField.getDocument().removeDocumentListener(this);
            notesField.getDocument().removeDocumentListener(this);
            jobCustomerNameField.getDocument().removeDocumentListener(this);
            siteContactField.getDocument().removeDocumentListener(this);
            jobPhoneField.getDocument().removeDocumentListener(this);
            jobAddressField.getDocument().removeDocumentListener(this);
            worksRequiredField.getDocument().removeDocumentListener(this);
            jobNotesField.getDocument().removeDocumentListener(this);
            furtherInstructionsField.getDocument().removeDocumentListener(this);
        }
    }

    private void loadEstimateDetails()
    {
        Estimate estimate = estimator.get();

        isEnableListeners(false);

        estimateNumberField.setText(config.getEstimatePrefix() + estimate.getReference());
        internalReferenceField.setText(estimate.getInternalReference());
        customerNameField.setText(estimate.getCustomerName());
        contactNameField.setText(estimate.getContactName());
        customerAddressField.setText(estimate.getCustomerAddress());
        homePhoneField.setText(estimate.getHomePhone());
        faxField.setText(estimate.getFax());
        workPhoneField.setText(estimate.getWorkPhone());
        mobileField.setText(estimate.getMobile());
        notesField.setText(estimate.getNotes());
        jobCustomerNameField.setText(estimate.getJobCustomerName());
        siteContactField.setText(estimate.getSiteContact());
        jobPhoneField.setText(estimate.getJobPhone());
        jobAddressField.setText(estimate.getJobAddress());
        worksRequiredField.setText(estimate.getWorksRequired());
        jobNotesField.setText(estimate.getJobNotes());
        furtherInstructionsField.setText(estimate.getFurtherInstructions());

        isEnableListeners(true);
    }

    public void changedUpdate(DocumentEvent e) 
    {
        updateCustomerDetails();
    }

    public void insertUpdate(DocumentEvent e)
    {
        updateCustomerDetails();
    }

    public void removeUpdate(DocumentEvent e) 
    {
    }

    private void updateCustomerDetails()
    {
        Estimate estimate = estimator.get();

        estimate.setInternalReference(internalReferenceField.getText());
        estimate.setCustomerName(customerNameField.getText());
        estimate.setContactName(contactNameField.getText());
        estimate.setCustomerAddress(customerAddressField.getText());
        estimate.setHomePhone(homePhoneField.getText());
        estimate.setFax(faxField.getText());
        estimate.setWorkPhone(workPhoneField.getText());
        estimate.setMobile(mobileField.getText());
        estimate.setNotes(notesField.getText());
        estimate.setJobCustomerName(jobCustomerNameField.getText());
        estimate.setSiteContact(siteContactField.getText());
        estimate.setJobPhone(jobPhoneField.getText());
        estimate.setJobAddress(jobAddressField.getText());
        estimate.setWorksRequired(worksRequiredField.getText());
        estimate.setJobNotes(jobNotesField.getText());
        estimate.setFurtherInstructions(furtherInstructionsField.getText());
    }

    private class AsAboveListener implements ActionListener
    {
        private JPanel parent;

        public AsAboveListener(JPanel parent)
        {
            this.parent = parent;
        }

        public void actionPerformed(ActionEvent ev)
        {
            jobAddressField.setText("As Above");
        }
    }
}
