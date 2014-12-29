package net.sourceforge.gator;

import java.util.List;

import net.sourceforge.gator.util.Initable;
import net.sourceforge.gator.util.XMLSerializer;

public interface Item extends Initable, XMLSerializer
{
    void setConfiguration(Configuration configuration);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    Float getCost();
    void setCost(String cost);
    List getHelpers();
    void addHelperRef(String helperRef);
    void setHelpers(HelperBundle helperBundle);
    void removeHelperRef(String helperRef);
    Field getField();
    void setField(Field field);
    void setFieldRef(String fieldRef);
    String toString();
}
