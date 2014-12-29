package net.sourceforge.gator.civil;

import net.sourceforge.gator.SubTask;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

public class TaskImplTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public TaskImplTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(TaskImplTest.class);
    }

    public void testGetProfitMargin()
        throws Exception
    {
        SubTask subTask1 = createSubTask("12.34", "5"); // 61.70
        SubTask subTask2 = createSubTask("34.56", "6"); // 207.36
        SubTask subTask3 = createSubTask("78.90", "7"); // 552.30

        // Total: 821.36

        TaskImpl task = new TaskImpl();

        Float quantity = new Float(20);
        task.setQuantity(quantity.toString());
        Float profitMarginPercentage = new Float(42.42);
        task.setProfitMarginPercentage(profitMarginPercentage.toString());
        task.addSubTask(subTask1);
        task.addSubTask(subTask2);
        task.addSubTask(subTask3);

        Float totalCost = task.getTotalCost();
        Float answer1 = new Float(821.36);
        Assert.assertTrue(answer1.equals(totalCost));

        Float charge = task.getCharge();
        float totalCostFloat = totalCost.floatValue();
        Float answer2 = new Float((totalCostFloat * (profitMarginPercentage.floatValue() / 100)) + totalCostFloat);
        Assert.assertTrue(answer2.equals(charge));

        Float chargeRate = task.getChargeRate();
        Float answer3 = new Float(charge.floatValue() / quantity.floatValue());
        Assert.assertTrue(answer3.equals(chargeRate));

    }

    private SubTask createSubTask(String unitCost, String quantity)
    {
        SubTaskImpl subTask = new SubTaskImpl();

        subTask.setUnitCost(unitCost);
        subTask.setQuantity(quantity);

        return subTask;
    }
}
