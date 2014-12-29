package net.sourceforge.gator;

import java.io.File;

import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRExporterParameter;

import dori.jasper.engine.export.JRXlsExporter;
import dori.jasper.engine.export.JRXlsExporterParameter;

import org.dom4j.Branch;
import org.dom4j.Element;

public class XlsJasperDestination implements JasperDestination
{
    private static final String XLS_SUFFIX = ".xls";

    private Configuration configuration;
    private JasperOutput output;

    public void init()
    {
    }

    public Branch asXML(Branch parent)
    {
        Element root = parent.addElement("xls");

        return root;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    public void setJasperOutput(JasperOutput output)
    {
        this.output = output;
    }

    public void process(Estimate estimate, JasperPrint print)
        throws EstimationException
    {
        try {
            String outputDirName = configuration.getOutputDirectory();
            String estimateDirName = configuration.getEstimatePrefix() + estimate.getReference();

            String estimateDir = createDirectory(outputDirName, estimateDirName);

            File destFile = new File(estimateDir, output.getName() + XLS_SUFFIX);

            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);

            exporter.exportReport();
        } catch (JRException e) {
            throw new EstimationException(e);
        }
    }

    private String createDirectory(String parentDir, String childDir)
    {
        File dir = new File(parentDir, childDir);

        if (!(dir.exists())) {
            dir.mkdir();
        }

        return dir.toString();
    }
}
