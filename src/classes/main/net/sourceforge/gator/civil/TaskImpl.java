package net.sourceforge.gator.civil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.EstimationException;
import net.sourceforge.gator.Job;
import net.sourceforge.gator.SubTask;
import net.sourceforge.gator.Task;

import net.sourceforge.gator.util.UniqueIdGenerator;

public class TaskImpl implements Task
{
    private String uniqueId;
    private String prefix = "";
    private Integer id = new Integer(0);
    private String jobDescription = "";
    private String quantityUnit = "";
    private Float quantity = new Float(0);
    private Float chargeRate = new Float(0);
    private List subTasks;
    private Float profitMarginPercentage = new Float(0);
    private Job job;

    public TaskImpl()
    {
        subTasks = new ArrayList();
        uniqueId = UniqueIdGenerator.get();
    }

    public String getUniqueId()
    {
        return uniqueId;
    }

    public Branch asXML(Branch parent)
    {
        Element task = parent.addElement("task");
        task.addAttribute("prefix", prefix);
        task.addAttribute("id", id.toString());
        task.addAttribute("profitMarginPercentage", profitMarginPercentage.toString());

        Element tSummary = task.addElement("summary");

        Element tJobDescription = tSummary.addElement("jobDescription");
        tJobDescription.addText(jobDescription);

        Element tQuantity = tSummary.addElement("quantity");
        tQuantity.addAttribute("unit", quantityUnit);
        tQuantity.addText(quantity.toString());

        Element tUnitCost = tSummary.addElement("chargeRate");
        tUnitCost.addText(chargeRate.toString());

        Element tSubTasks = task.addElement("subTasks");
        for (Iterator i = subTasks.iterator(); i.hasNext();) {
            SubTask subTask = (SubTask) i.next();
            subTask.asXML(tSubTasks);
        }

        return task;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getJobDescription()
    {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription)
    {
        this.jobDescription = jobDescription;
    }

    public String getQuantityUnit()
    {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit)
    {
        this.quantityUnit = quantityUnit;
    }

    public Float getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = new Float(quantity);
    }

    public Float getProfitMarginPercentage()
    {
        return profitMarginPercentage;
    }

    public void setProfitMarginPercentage(String profitMarginPercentage)
    {
        this.profitMarginPercentage = new Float(profitMarginPercentage);
    }

    public Float getTotalCost()
        throws EstimationException
    {
        float cost = 0;

        for (Iterator i = subTasks.iterator(); i.hasNext();) {
            SubTask subTask = (SubTask) i.next();

            Float subTaskCost = subTask.getTotalCost();
            cost += subTaskCost.floatValue();
        }

        return new Float(cost);
    }

    public Float getCharge()
        throws EstimationException
    {
        float pcnt = profitMarginPercentage.floatValue();
        float cost = getTotalCost().floatValue();

        return new Float((cost * (pcnt / 100)) + cost);
    }

    public Float getChargeRate()
        throws EstimationException
    {
        float charge = getCharge().floatValue();

        return new Float(charge / quantity.floatValue());
    }

    public List getSubTasks()
    {
        return subTasks;
    }

    public void addSubTask(SubTask subTask)
    {
        subTask.setTask(this);
        subTasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask)
    {
        subTasks.remove(subTask);
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append(prefix);
        sb.append(id);
        sb.append(") ");
        sb.append(jobDescription);
        sb.append(" [");
        sb.append(quantity.toString());
        sb.append(quantityUnit);
        sb.append(", ");
        sb.append(profitMarginPercentage.toString());
        sb.append("%]");

        return sb.toString();
    }

    public Job getJob()
    {
        return job;
    }

    public void setJob(Job job)
    {
        this.job = job;
    }
}
