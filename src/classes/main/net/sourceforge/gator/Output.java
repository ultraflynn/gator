package net.sourceforge.gator;

import net.sourceforge.gator.util.Initable;
import net.sourceforge.gator.util.XMLSerializer;

public interface Output extends Initable, XMLSerializer
{
    void setConfiguration(Configuration configuration);
    String getName();
    String getDescription();
    void process(Estimate estimate)
        throws EstimationException;
}
