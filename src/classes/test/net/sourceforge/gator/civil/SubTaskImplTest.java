package net.sourceforge.gator.civil;

import net.sourceforge.gator.EstimationException;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

public class SubTaskImplTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public SubTaskImplTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(SubTaskImplTest.class);
    }

    public void testGetTotalCost()
        throws Exception
    {
        SubTaskImpl subTask = new SubTaskImpl();

        try {
            subTask.getTotalCost();
            fail("Should have caught an EstimationException");
        } catch (EstimationException e) {
            Assert.assertEquals("Cannot calculate cost of Sub-Task as unit cost has not been set", e.getMessage());
        }

        subTask.setUnitCost("42.42");

        try {
            subTask.getTotalCost();
            fail("Should have caught an EstimationException");
        } catch (EstimationException e) {
            Assert.assertEquals("Cannot calculate cost of Sub-Task as quantity has not been set", e.getMessage());
        }

        subTask.setQuantity("4");

        Float cost = subTask.getTotalCost();
        Assert.assertEquals(new Float(169.68), cost);
    }
}
