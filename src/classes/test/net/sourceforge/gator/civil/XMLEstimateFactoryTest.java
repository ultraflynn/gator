package net.sourceforge.gator.civil;

import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.EstimateFactory;
import net.sourceforge.gator.EstimateFactoryBuilder;
import net.sourceforge.gator.Job;
import net.sourceforge.gator.SubTask;
import net.sourceforge.gator.Task;

import net.sourceforge.gator.util.PropertyManager;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

public class XMLEstimateFactoryTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public XMLEstimateFactoryTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(XMLEstimateFactoryTest.class);
    }

    public void testRoot()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/estimate-test1.properties");
        EstimateFactory factory = EstimateFactoryBuilder.getInstance();

        Estimate estimate = factory.getEstimate("./build/classes/test/net/sourceforge/gator/civil/estimate-test1.xml");
        Assert.assertTrue(estimate instanceof EstimateImpl);
    }

    public void testCustomer()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/estimate-test1.properties");
        EstimateFactory factory = EstimateFactoryBuilder.getInstance();

        Estimate estimate = factory.getEstimate("./build/classes/test/net/sourceforge/gator/civil/estimate-test1.xml");
        EstimateImpl cee = (EstimateImpl) estimate;

        Assert.assertEquals("REFERENCE", cee.getReference());
        Assert.assertEquals("INTERNAL REFERENCE", cee.getInternalReference());
        Assert.assertEquals("DATE", cee.getDate());
        Assert.assertEquals("CUSTOMER NAME", cee.getCustomerName());
        Assert.assertEquals("CONTACT NAME", cee.getContactName());
        Assert.assertEquals("Address Line 1\nAddress Line 2\nAddress Line 3", cee.getCustomerAddress());
        Assert.assertEquals("HOME PHONE", cee.getHomePhone());
        Assert.assertEquals("FAX", cee.getFax());
        Assert.assertEquals("WORK PHONE", cee.getWorkPhone());
        Assert.assertEquals("MOBILE", cee.getMobile());
        Assert.assertEquals("NOTES", cee.getNotes());

        Assert.assertEquals("JOB DETAILS CUSTOMER NAME", cee.getJobCustomerName());
        Assert.assertEquals("JOB DETAILS JOB PHONE", cee.getJobPhone());
        Assert.assertEquals("Job Details Address Line 1\nJob Details Address Line 2\nJob Details Address Line 3", cee.getJobAddress());
        Assert.assertEquals("JOB DETAILS SITE CONTACT", cee.getSiteContact());
    }

    public void testJobs()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/estimate-test1.properties");
        EstimateFactory factory = EstimateFactoryBuilder.getInstance();

        Estimate estimate = factory.getEstimate("./build/classes/test/net/sourceforge/gator/civil/estimate-test1.xml");
        EstimateImpl cee = (EstimateImpl) estimate;

        List jobs = cee.getJobs();
        Assert.assertEquals(jobs.size(), 1);

        Iterator i = jobs.iterator();
        Job job = (Job) i.next();

        Assert.assertEquals("JOB DESCRIPTION", job.getDescription());
        Float quantity = job.getQuantity();
        Assert.assertEquals(new Float(12.23), quantity);
        Assert.assertEquals("QUANTITY UNIT", job.getQuantityUnit());
        Assert.assertEquals("QUANTITY PREFIX", job.getQuantityPrefix());
    
        List tasks = job.getTasks();
        Assert.assertEquals(tasks.size(), 1);

        Iterator j = tasks.iterator();
        Task task = (Task) j.next();

        Assert.assertEquals("PREFIX", task.getPrefix());
        Assert.assertEquals(new Integer(5), task.getId());
        Assert.assertEquals("JOB DESCRIPTION", task.getJobDescription());
        Assert.assertEquals("SUMMARY QUANTITY UNIT", task.getQuantityUnit());
        Float summaryQuantity = task.getQuantity();
        Assert.assertEquals(new Float(45.56), summaryQuantity);
        Float profitMarginPercentage = task.getProfitMarginPercentage();
        Assert.assertEquals(new Float(10.24), profitMarginPercentage);

        List subTasks = task.getSubTasks();
        Assert.assertEquals(subTasks.size(), 1);

        Iterator k = subTasks.iterator();
        SubTask subTask = (SubTask) k.next();
        
        Assert.assertEquals("SUBTASK TYPE", subTask.getType());
        Assert.assertEquals("SUBTASK DESCRIPTION", subTask.getDescription());
        Assert.assertEquals("SUBTASK CODE", subTask.getCode());
        Assert.assertEquals("SUBTASK QUANTITY UNIT", subTask.getQuantityUnit());
        Float subTaskQuantity = subTask.getQuantity();
        Assert.assertEquals(new Float(56.67), subTaskQuantity);
        Float subTaskUnitCost = subTask.getUnitCost();
        Assert.assertEquals(new Float(78.89), subTaskUnitCost);
    }

    public void testStrangeChars()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/estimate-test1.properties");
        EstimateFactory factory = EstimateFactoryBuilder.getInstance();

        Estimate estimate = factory.getEstimate("./build/classes/test/net/sourceforge/gator/civil/estimate-test2.xml");
        EstimateImpl cee = (EstimateImpl) estimate;

        Assert.assertEquals("³", cee.getWorkPhone());
        Assert.assertEquals("½", cee.getJobCustomerName());
    }
}
