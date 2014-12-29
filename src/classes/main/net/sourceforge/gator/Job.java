package net.sourceforge.gator;

import java.util.List;

import org.dom4j.Branch;

import net.sourceforge.gator.util.XMLSerializer;

public interface Job extends XMLSerializer
{
    String getUniqueId();
    Branch asXML(Branch parent);

    String getDescription();
    void setDescription(String description);
    String getQuantityUnit();
    void setQuantityUnit(String quantityUnit);
    Float getQuantity();
    void setQuantity(String quantity);
    String getQuantityPrefix();
    void setQuantityPrefix(String quantityPrefix);

    List getTasks();
    void addTask(Task task);
    void removeTask(Task task);

    String toString();

    Estimate getEstimate();
    void setEstimate(Estimate estimate);
}
