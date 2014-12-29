package net.sourceforge.gator;

import java.util.List;

import org.dom4j.Branch;

import net.sourceforge.gator.util.XMLSerializer;

public interface Task extends XMLSerializer
{
    String getUniqueId();
    Branch asXML(Branch parent);

    String getPrefix();
    void setPrefix(String prefix);
    Integer getId();
    void setId(Integer id);
    String getJobDescription();
    void setJobDescription(String jobDescription);
    
    String getQuantityUnit();
    void setQuantityUnit(String quantityUnit);

    Float getQuantity();
    void setQuantity(String quantity);
    Float getProfitMarginPercentage();
    void setProfitMarginPercentage(String profitMarginPercentage);

    Float getTotalCost()
        throws EstimationException;
    Float getCharge()
           throws EstimationException;
    Float getChargeRate()
        throws EstimationException;

    List getSubTasks();
    void addSubTask(SubTask subTask);
    void removeSubTask(SubTask subTask);

    String toString();

    Job getJob();
    void setJob(Job job);
}
