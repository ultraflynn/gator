package net.sourceforge.gator;

import net.sourceforge.gator.util.Initable;
import net.sourceforge.gator.util.XMLSerializer;

public interface Helper extends Initable, XMLSerializer
{
    void setConfiguration(Configuration configuration);
    String getName();
    String getDescription();
    HelperUI getUIClass();
    String toString();
}
