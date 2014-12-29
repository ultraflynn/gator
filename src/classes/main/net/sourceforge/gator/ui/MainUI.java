package net.sourceforge.gator.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.EstimationException;
import net.sourceforge.gator.Estimator;

public class MainUI extends JPanel
{
    public static final String WINDOW_TITLE = "Gator Estimation";

    private FontRegistry fontRegistry;
    private Configuration config;
    private Estimator estimator;
    private CustomerDetailsTabPage customerTab;
    private EstimateTabPage estimateTab;

    public MainUI()
    {
        super(true);

        try {
            String laf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);
        } catch (ClassNotFoundException e) {
            System.out.println("Look & Feel Error: Class not found");
        } catch (InstantiationException e) {
            System.out.println("Look & Feel Error: Instantiation error");
        } catch (IllegalAccessException e) {
            System.out.println("Look & Feel Error: Illegal access");
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Look & Feel Error: Unsupported look and feel");
        }
    }

    public static void main(String[] args)
    {
        new MainUI().run(args);
    }

    public void run(String[] args)
    {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload()
    {
        customerTab.reload();
        estimateTab.reload();
    }

    private void init()
        throws Exception
    {
        estimator = new Estimator();

        config = estimator.getConfiguration();

        JFrame frame = new JFrame(WINDOW_TITLE);
        JPanel main = new JPanel();

        int rtn = JOptionPane.showConfirmDialog(null, "Would you like to create a new estimate? Please 'No' to open an existing estimate", "Create New Estimate", JOptionPane.YES_NO_OPTION);

        if (rtn == JOptionPane.YES_OPTION) {
            estimator.create();
        } else {
            OpenEstimateDialog dialog = new OpenEstimateDialog(estimator);
            File selectedEstimate = dialog.show(main);

            if (selectedEstimate != null) {
                String name = selectedEstimate.getName();
                String ref = selectedEstimate.getParentFile().getName();
                File fullName = new File(ref, name);
                estimator.load(fullName.toString());
            }
        }

        fontRegistry = new FontRegistry();
        fontRegistry.add("NORMAL", "SansSerif", Font.PLAIN, 10);
        fontRegistry.add("H1", "SansSerif", Font.BOLD, 14);

        BorderLayout layout = new BorderLayout();
        main.setLayout(layout);

        Container content = frame.getContentPane();

        addContent(main);

        content.add(main, BorderLayout.CENTER);

        frame.addWindowListener(new AppCloseEvent());
        frame.pack();
        frame.setVisible(true);
    }

    private void addContent(Container content)
        throws Exception
    {
        JTabbedPane tabpane = initTabPane(content);

        customerTab = new CustomerDetailsTabPage();
        customerTab.setConfiguration(config);
        customerTab.setEstimator(estimator);
        customerTab.setMainUI(this);
        customerTab.init();
        customerTab.addToTab(tabpane);

        estimateTab = new EstimateTabPage();
        estimateTab.setConfiguration(config);
        estimateTab.setEstimator(estimator);
        estimateTab.setMainUI(this);
        estimateTab.init();
        estimateTab.addToTab(tabpane);

        content.add(tabpane);
    }

    private JTabbedPane initTabPane(Container content)
        throws Exception
    {
        JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
        pane.setPreferredSize(new Dimension(700, 700));
        return pane;
    }

    private class AppCloseEvent extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            try {
                int rtn = JOptionPane.showConfirmDialog(null, "Would you like to save this estimate?", "Save Estimate", JOptionPane.YES_NO_OPTION);

                if (rtn == JOptionPane.YES_OPTION) {
                    estimator.save();
                }
            } catch (EstimationException e) {
                e.printStackTrace();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }

            System.exit(0);
        }
    }
}
