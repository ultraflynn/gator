package net.sourceforge.gator;

import java.io.File;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import net.sourceforge.gator.util.PropertyManager;

import net.sourceforge.gator.civil.ConfigurationImpl;

public class EstimatorTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public EstimatorTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(EstimatorTest.class);
    }

    public void testExceptionCreation()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/estimator-test1.properties");
        Estimator estimator = new Estimator();
        
        try {
            estimator.save();
            fail("Should have caught an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
        
        try {
            estimator.get();
            fail("Should have caught an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
        
        try {
            estimator.print();
            fail("Should have caught an IllegalStateException");
        } catch (IllegalStateException e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    public void testCreate()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/estimator-test1.properties");
        Estimator estimator = new Estimator();

        Estimate estimate = estimator.create();
        Assert.assertEquals("00000001", estimate.getReference());
    }

    public void testLoad()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/estimator-test1.properties");
        Estimator estimator = new Estimator();

        Estimate estimate = estimator.load("estimator-estimate-test1.xml");
        Assert.assertEquals("REFERENCE", estimate.getReference());
    }

    public void testSaveEstimate()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/estimator-test1.properties");
        Estimator estimator1 = new Estimator();

        Estimate estimate1 = estimator1.load("estimator-estimate-test1.xml");
        Assert.assertEquals("REFERENCE", estimate1.getReference());

        estimate1.setReference("estimator-estimate-test2");
        estimator1.save();

        Estimator estimator2 = new Estimator();
        Estimate estimate2 = estimator2.load("estimator-estimate-test2\\estimator-estimate-test2.xml");
        Assert.assertEquals("estimator-estimate-test2", estimate2.getReference());

        String newEstimateFileName = ".\\build\\classes\\int-test\\org\\sourceforge\\gator\\estimator-estimate-test2\\estimator-estimate-test2.xml";
        File newEstimateFile = new File(newEstimateFileName);
        newEstimateFile.delete();
        File newEstimateDir = new File(".\\build\\classes\\int-test\\org\\sourceforge\\gator\\estimator-estimate-test2");
        newEstimateDir.delete();
    }

    public void testGet()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/estimator-test1.properties");
        Estimator estimator = new Estimator();

        Estimate estimate1 = estimator.load("estimator-estimate-test1.xml");
        Estimate estimate2 = estimator.get();

        Assert.assertEquals(estimate1, estimate2);
    }

    public void testSaveConfiguration()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/estimator-test1.properties");
        Estimator estimator1 = new Estimator();
        
        ConfigurationImpl config1 = (ConfigurationImpl) estimator1.getConfiguration();
        config1.setReferenceLength(20);

        estimator1.saveConfiguration();

        Estimator estimator2 = new Estimator();
        Configuration config2 = estimator2.getConfiguration();

        Assert.assertTrue(config1 != config2);
        Assert.assertTrue(!(config1.equals(config2)));

        config1.setReferenceLength(8);
        estimator1.saveConfiguration();
    }

    public void testPrint()
        throws Exception
    {
        // TODO
    }
}
