package net.sourceforge.gator.ui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class EstimateFileFilter extends FileFilter
{
    public boolean accept(File file)
    {
        if (file == null) {
            return false;
        }

        if (file.isDirectory()) {
            return true;
        }

        return (getExtension(file).equals("xml"));
    }

    public String getDescription()
    {
        return "Saved Estimates (*.xml)";
    }

    public String getExtension(File f)
    {
        if (f == null) {
            return null;
        }

        String filename = f.getName();
        int i = filename.lastIndexOf('.');

        if (i > 0 && i < filename.length() - 1) {
            return filename.substring(i + 1).toLowerCase();
        }

        return null;
    }
}
