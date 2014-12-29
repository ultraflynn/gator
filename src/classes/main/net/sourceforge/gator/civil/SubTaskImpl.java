package net.sourceforge.gator.civil;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.EstimationException;
import net.sourceforge.gator.Task;
import net.sourceforge.gator.SubTask;

import net.sourceforge.gator.util.UniqueIdGenerator;

public class SubTaskImpl implements SubTask
{
    private static final String PLANT = "plant";
    private static final String LABOUR = "labour";
    private static final String MATERIAL = "material";
    
    private String uniqueId;
    private String type = "";
    private String description = "";
    private String code = "";
    private String quantityUnit = "";
    private Float quantity = new Float(0);
    private Float unitCost = new Float(0);
    private Task task;

    public SubTaskImpl()
    {
        uniqueId = UniqueIdGenerator.get();
    }

    public String getUniqueId()
    {
        return uniqueId;
    }

    public Branch asXML(Branch parent)
    {
        Element subTask = parent.addElement("subTask");
        subTask.addAttribute("type", type);
        subTask.addAttribute("description", description);
        subTask.addAttribute("code", code);

        Element stQuantity = subTask.addElement("quantity");
        stQuantity.addAttribute("unit", quantityUnit);
        stQuantity.addText(quantity.toString());

        Element stUnitCost = subTask.addElement("unitCost");
        stUnitCost.addText(unitCost.toString());

        return subTask;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

    public Float getUnitCost()
    {
        return unitCost;
    }

    public void setUnitCost(String unitCost)
    {
        this.unitCost = new Float(unitCost);
    }

    public Float getTotalCost()
        throws EstimationException
    {
        if (unitCost == null || unitCost.equals(new Float(0))) {
            throw new EstimationException("Cannot calculate cost of Sub-Task as unit cost has not been set");
        }
        if (quantity == null || quantity.equals(new Float(0))) {
            throw new EstimationException("Cannot calculate cost of Sub-Task as quantity has not been set");
        }

        float a = unitCost.floatValue();
        float b = quantity.floatValue();

        return new Float(a * b);
    }

    public String toString()
    {
        return description + " [" + quantity + quantityUnit + "]"; // FIXME This needs expanding
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }
}
