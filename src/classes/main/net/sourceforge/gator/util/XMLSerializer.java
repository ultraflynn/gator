package net.sourceforge.gator.util;

import org.dom4j.Branch;

public interface XMLSerializer
{
    Branch asXML(Branch parent);
}
