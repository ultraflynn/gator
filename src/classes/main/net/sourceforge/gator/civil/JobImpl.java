package net.sourceforge.gator.civil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.Job;
import net.sourceforge.gator.Task;

import net.sourceforge.gator.util.UniqueIdGenerator;

public class JobImpl implements Job
{
    private String uniqueId;
    private String description = "";
    private String quantityUnit = "";
    private Float quantity = new Float(0);
    private String quantityPrefix = "";
    private List tasks;
    private Estimate estimate;

    public JobImpl()
    {
        tasks = new ArrayList();
        uniqueId = UniqueIdGenerator.get();
    }

    public String getUniqueId()
    {
        return uniqueId;
    }

    public Branch asXML(Branch parent)
    {
        Element job = parent.addElement("job");

        Element jDescription = job.addElement("description");
        jDescription.addText(description);

        Element jQuantity = job.addElement("quantity");
        jQuantity.addAttribute("prefix", quantityPrefix);
        jQuantity.addAttribute("unit", quantityUnit);
        jQuantity.addText(quantity.toString());

        for (Iterator i = tasks.iterator(); i.hasNext();) {
            Task task = (Task) i.next();
            task.asXML(job);
        }

        return job;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public String getQuantityPrefix()
    {
        return quantityPrefix;
    }

    public void setQuantityPrefix(String quantityPrefix)
    {
        this.quantityPrefix = quantityPrefix;
    }

    public List getTasks()
    {
        return tasks;
    }

    public void addTask(Task task)
    {
        task.setJob(this);
        tasks.add(task);
    }

    public void removeTask(Task task)
    {
        tasks.remove(task);
    }

    public String toString()
    {
        return description + " (" + quantityPrefix + " " + quantity.toString() + quantityUnit + ")";
    }

    public Estimate getEstimate()
    {
        return estimate;
    }

    public void setEstimate(Estimate estimate)
    {
        this.estimate = estimate;
    }
}
