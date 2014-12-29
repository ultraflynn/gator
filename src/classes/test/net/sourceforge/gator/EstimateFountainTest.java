package net.sourceforge.gator;

import java.io.File;

import net.sourceforge.gator.util.PropertyManager;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import org.easymock.EasyMock;
import org.easymock.MockControl;

public class EstimateFountainTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public EstimateFountainTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(EstimateFountainTest.class);
    }

    public void testGetInstance()
        throws Exception
    {
        MockControl configControl = EasyMock.controlFor(Configuration.class);
        Configuration mockConfig = (Configuration) configControl.getMock();

        configControl.activate();

        EstimateFountain fountain1 = EstimateFountain.getInstance(mockConfig);
        EstimateFountain fountain2 = EstimateFountain.getInstance(mockConfig);
        Assert.assertEquals(fountain1, fountain2);

        configControl.verify();
    }

    public void testCreate()
        throws Exception
    {
        File estimateDirectory = new File("build/classes/test/net/sourceforge/gator/00000007");
        if (!(estimateDirectory.exists())) {
            estimateDirectory.mkdir();
        }

        PropertyManager.getInstance("net/sourceforge/gator/estimate-fountain-test.properties");

        MockControl configControl = EasyMock.controlFor(Configuration.class);
        Configuration mockConfig = (Configuration) configControl.getMock();

        mockConfig.getEstimateDirectory();
        configControl.setReturnValue("./build/classes/test/net/sourceforge/gator");
        mockConfig.getReferenceLength();
        configControl.setReturnValue(8);

        configControl.activate();

        EstimateFountain fountain = EstimateFountain.getInstance(mockConfig);
        Estimate estimate = fountain.create();

        Assert.assertEquals("00000008", estimate.getReference());

        configControl.verify();
    }
}
