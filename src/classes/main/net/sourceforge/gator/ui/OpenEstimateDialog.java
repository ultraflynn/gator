package net.sourceforge.gator.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import javax.swing.filechooser.FileFilter;

import net.sourceforge.gator.Estimator;
import net.sourceforge.gator.Configuration;

public class OpenEstimateDialog
{
    private static final String WINDOW_TITLE = "Open Estimate";
    private Estimator estimator;

    private JFileChooser fileChooser;

    public OpenEstimateDialog(Estimator estimator)
    {
        this.estimator = estimator;

        Configuration config = estimator.getConfiguration();
        String dirName = config.getEstimateDirectory();
        File startDir = new File(dirName);
        fileChooser = new JFileChooser(startDir);
        FileFilter filter = new EstimateFileFilter();
        fileChooser.setFileFilter(filter);
    }

    public File show(JPanel parent)
    {
        File file = null;

        int returnVal = fileChooser.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }

        return file;
    }
}
