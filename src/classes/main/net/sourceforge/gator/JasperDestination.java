package net.sourceforge.gator;

import dori.jasper.engine.JasperPrint;

import net.sourceforge.gator.util.Initable;
import net.sourceforge.gator.util.XMLSerializer;

public interface JasperDestination extends Initable, XMLSerializer
{
    void setConfiguration(Configuration configuration);
    void setJasperOutput(JasperOutput output);
    void process(Estimate estimate, JasperPrint print)
        throws EstimationException;
}
