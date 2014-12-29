package net.sourceforge.gator;

import org.dom4j.Branch;

import net.sourceforge.gator.util.XMLSerializer;

public interface SubTask extends XMLSerializer
{
    String getUniqueId();
    Branch asXML(Branch parent);

    String getType();
    void setType(String type);
    String getDescription();
    void setDescription(String description);
    String getCode();
    void setCode(String code);
    String getQuantityUnit();
    void setQuantityUnit(String quantityUnit);

    Float getQuantity();
    void setQuantity(String quantity);
    Float getUnitCost();
    void setUnitCost(String unitCost);

    Float getTotalCost()
        throws EstimationException;

    String toString();

    Task getTask();
    void setTask(Task task);
}
