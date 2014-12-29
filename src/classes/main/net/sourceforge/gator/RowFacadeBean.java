package net.sourceforge.gator;

public class RowFacadeBean
{
    private Job job;
    private Task task;
    private SubTask subTask;

    RowFacadeBean(Job job, Task task, SubTask subTask)
    {
        this.job = job;
        this.task = task;
        this.subTask = subTask;
    }

    public String getJobGroupId()
    {
        return job.getUniqueId();
    }

    public String getJobDescription()
    {
        return job.getDescription();
    }

    public String getJobQuantityUnit()
    {
        return job.getQuantityUnit();
    }

    public Float getJobQuantity()
    {
        return job.getQuantity();
    }

    public String getJobQuantityPrefix()
    {
        return job.getQuantityPrefix();
    }

    public String getTaskGroupId()
    {
        return task.getUniqueId();
    }

    public String getTaskPrefix()
    {
        return task.getPrefix();
    }

    public Integer getTaskId()
    {
        return task.getId();
    }

    public String getTaskJobDescription()
    {
        return task.getJobDescription();
    }

    public String getTaskQuantityUnit()
    {
        return task.getQuantityUnit();
    }

    public Float getTaskQuantity()
    {
        return task.getQuantity();
    }

    public Float getTaskProfitMarginPercentage()
    {
        return task.getProfitMarginPercentage();
    }

    public Float getTaskTotalCost()
        throws EstimationException
    {
        return task.getTotalCost();
    }

    public Float getTaskCharge()
        throws EstimationException
    {
        return task.getCharge();
    }

    public Float getTaskChargeRate()
        throws EstimationException
    {
        return task.getChargeRate();
    }

    public String getSubTaskGroupId()
    {
        return subTask.getUniqueId();
    }

    public String getSubTaskType()
    {
        return subTask.getType();
    }

    public String getSubTaskDescription()
    {
        return subTask.getDescription();
    }

    public String getSubTaskCode()
    {
        return subTask.getCode();
    }

    public String getSubTaskQuantityUnit()
    {
        return subTask.getQuantityUnit();
    }

    public Float getSubTaskQuantity()
    {
        return subTask.getQuantity();
    }

    public Float getSubTaskUnitCost()
    {
        return subTask.getUnitCost();
    }

    public Float getSubTaskTotalCost()
        throws EstimationException
    {
        return subTask.getTotalCost();
    }
}
