package net.sourceforge.gator;

import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRExporterParameter;

import dori.jasper.engine.export.JRPrintServiceExporter;
import dori.jasper.engine.export.JRPrintServiceExporterParameter;

import org.dom4j.Branch;
import org.dom4j.Element;

public class PrintedJasperDestination implements JasperDestination
{
    private Configuration configuration;
    private JasperOutput output;
    private Boolean displayPrintDialog;

    public void init()
    {
    }

    public Branch asXML(Branch parent)
    {
        Element root = parent.addElement("printed");
        root.addAttribute("displayPrintDialog", displayPrintDialog.toString());

        return root;
    }

    public Boolean isDisplayPrintDialog()
    {
        return displayPrintDialog;
    }

    public void setDisplayPrintDialog(Boolean displayPrintDialog)
    {
        this.displayPrintDialog = displayPrintDialog;
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
            JRPrintServiceExporter exporter = new JRPrintServiceExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG , displayPrintDialog);

            exporter.exportReport();
        } catch (JRException e) {
            throw new EstimationException(e);
        }
    }
}
