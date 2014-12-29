package net.sourceforge.gator.civil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.ConfigurationFactory;
import net.sourceforge.gator.ConfigurationFactoryBuilder;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.FieldUI;
import net.sourceforge.gator.Item;
import net.sourceforge.gator.Helper;
import net.sourceforge.gator.HelperUI;
import net.sourceforge.gator.JasperDestination;
import net.sourceforge.gator.JasperOutput;
import net.sourceforge.gator.Output;
import net.sourceforge.gator.XlsJasperDestination;

import net.sourceforge.gator.fields.DummyFieldUI;

import net.sourceforge.gator.helpers.DummyHelperUI;

import net.sourceforge.gator.util.PropertyManager;

public class XMLConfigurationFactoryTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public XMLConfigurationFactoryTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(XMLConfigurationFactoryTest.class);
    }

    public void testRoot()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/configuration-test1.properties");
        ConfigurationFactory factory = ConfigurationFactoryBuilder.getInstance();

        Configuration configuration = factory.getConfiguration();
        Assert.assertTrue(configuration instanceof ConfigurationImpl);

        ConfigurationImpl cec = (ConfigurationImpl) configuration;
        Assert.assertEquals("ESTIMATE DIRECTORY", cec.getEstimateDirectory());
        Assert.assertEquals("ESTIMATE PREFIX", cec.getEstimatePrefix());
        Assert.assertEquals("ESTIMATE SUFFIX", cec.getEstimateSuffix());
        Assert.assertEquals(8, cec.getReferenceLength());
        Assert.assertEquals("Output Directory", cec.getOutputDirectory());
        Assert.assertEquals("Categories", cec.getRootCategoryName());
    }

    public void testHelpers()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/configuration-test1.properties");
        ConfigurationFactory factory = ConfigurationFactoryBuilder.getInstance();

        Configuration configuration = factory.getConfiguration();
        ConfigurationImpl cec = (ConfigurationImpl) configuration;

        Set helperNames = cec.getHelperNames();
        Assert.assertEquals(helperNames.size(), 1);

        Iterator i = helperNames.iterator();
        String helperName = (String) i.next();
        Helper helper = cec.getHelper(helperName);
        Assert.assertEquals(helper.getName(), "helper");
        Assert.assertEquals(helper.getDescription(), "dummy helper");

        HelperUI helperUI = helper.getUIClass();
        if (helperUI == null) {
            fail("Could not obtain the helper");
        }
        Assert.assertTrue(helperUI instanceof DummyHelperUI);
    }

    public void testFields()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/configuration-test1.properties");
        ConfigurationFactory factory = ConfigurationFactoryBuilder.getInstance();

        Configuration configuration = factory.getConfiguration();
        ConfigurationImpl cec = (ConfigurationImpl) configuration;

        Set fieldNames = cec.getFieldNames();
        Assert.assertEquals(fieldNames.size(), 1);

        Iterator i = fieldNames.iterator();
        String fieldName = (String) i.next();
        Field field = cec.getField(fieldName);
        Assert.assertEquals(field.getName(), "field");
        Assert.assertEquals(field.getDescription(), "dummy field");

        FieldUI fieldUI = field.getUIClass();
        if (fieldUI == null) {
            fail("Could not obtain the field");
        }
        Assert.assertTrue(fieldUI instanceof DummyFieldUI);
    }

    public void testItems()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/configuration-test1.properties");
        ConfigurationFactory factory = ConfigurationFactoryBuilder.getInstance();

        Configuration configuration = factory.getConfiguration();
        ConfigurationImpl cec = (ConfigurationImpl) configuration;

        Set itemNames = cec.getItemNames();
        Assert.assertEquals(itemNames.size(), 1);

        Iterator i = itemNames.iterator();
        String itemName = (String) i.next();
        Item item = cec.getItem(itemName);
        Assert.assertEquals(item.getName(), "item");
        Assert.assertEquals(item.getDescription(), "dummy item");
        Assert.assertEquals(item.getCost(), new Float(42.42));

        List helpers = item.getHelpers();
        Assert.assertEquals(helpers.size(), 1);
        Iterator j = helpers.iterator();
        Helper helper = (Helper) j.next();
        Assert.assertEquals(helper.getName(), "helper");
        Assert.assertEquals(helper.getDescription(), "dummy helper");

        HelperUI helperUI = helper.getUIClass();
        if (helperUI == null) {
            fail("Could not obtain the helper");
        }
        Assert.assertTrue(helperUI instanceof DummyHelperUI);

        Field field = item.getField();
        Assert.assertEquals(field.getName(), "field");
        Assert.assertEquals(field.getDescription(), "dummy field");

        FieldUI fieldUI = field.getUIClass();
        if (fieldUI == null) {
            fail("Could not obtain the field");
        }
        Assert.assertTrue(fieldUI instanceof DummyFieldUI);
    }

    public void testCategories()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/configuration-test1.properties");
        ConfigurationFactory factory = ConfigurationFactoryBuilder.getInstance();

        Configuration configuration = factory.getConfiguration();
        ConfigurationImpl cec = (ConfigurationImpl) configuration;

        List categories = cec.getCategories();
        Assert.assertEquals("No root category found -", categories.size(), 1);

        Iterator i = categories.iterator();
        Category cat1 = (Category) i.next();
        Assert.assertEquals(cat1.getName(), "category1");
        Assert.assertEquals(cat1.getDescription(), "Category 1");

        List cat1ItemNames = cat1.getItemNames();
        Assert.assertEquals(cat1ItemNames.size(), 0);

        List cat1SubCategories = cat1.getSubCategories();
        Assert.assertEquals("No sub-category found -", cat1SubCategories.size(), 1);

        Iterator j = cat1SubCategories.iterator();
        Category cat2 = (Category) j.next();
        Assert.assertEquals(cat2.getName(), "category2");
        Assert.assertEquals(cat2.getDescription(), "Category 2");

        List cat2ItemNames = cat2.getItemNames();
        Assert.assertEquals(cat2ItemNames.size(), 1);

        Iterator k = cat2ItemNames.iterator();
        String itemName = (String) k.next();
        Item item = cec.getItem(itemName);
        Assert.assertEquals(item.getName(), "item");
        Assert.assertEquals(item.getDescription(), "dummy item");
        Assert.assertEquals(item.getCost(), new Float(42.42));

        List helpers = item.getHelpers();
        Assert.assertEquals(helpers.size(), 1);
        Iterator l = helpers.iterator();
        Helper helper = (Helper) l.next();
        Assert.assertEquals(helper.getName(), "helper");
        Assert.assertEquals(helper.getDescription(), "dummy helper");

        HelperUI helperUI = helper.getUIClass();
        if (helperUI == null) {
            fail("Could not obtain the helper");
        }
        Assert.assertTrue(helperUI instanceof DummyHelperUI);

        Field field = item.getField();
        Assert.assertEquals(field.getName(), "field");
        Assert.assertEquals(field.getDescription(), "dummy field");

        FieldUI fieldUI = field.getUIClass();
        if (fieldUI == null) {
            fail("Could not obtain the field");
        }
        Assert.assertTrue(fieldUI instanceof DummyFieldUI);

        List cat2SubCategories = cat2.getSubCategories();
        Assert.assertEquals(cat2SubCategories.size(), 0);
    }

    public void testComplexCategories()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/configuration-test2.properties");
        ConfigurationFactory factory = ConfigurationFactoryBuilder.getInstance();

        Configuration configuration = factory.getConfiguration();
        Assert.assertTrue(configuration instanceof ConfigurationImpl);

        ConfigurationImpl cec = (ConfigurationImpl) configuration;

        List categories = cec.getCategories();
        Assert.assertEquals(categories.size(), 1);

        Iterator i = categories.iterator();
        Category cat1 = (Category) i.next();

        List cat1ItemNames = cat1.getItemNames();
        Assert.assertEquals(cat1ItemNames.size(), 3);

        List cat1SubCats = cat1.getSubCategories();
        Assert.assertEquals(cat1SubCats.size(), 2);

        Iterator j = cat1SubCats.iterator();
        Category cat2 = (Category) j.next();

        List cat2ItemNames = cat2.getItemNames();
        Assert.assertEquals(cat2ItemNames.size(), 2);

        List cat2SubCats = cat2.getSubCategories();
        Assert.assertEquals(cat2SubCats.size(), 1);

        Iterator k = cat2SubCats.iterator();
        Category cat3 = (Category) k.next();

        List cat3ItemNames = cat3.getItemNames();
        Assert.assertEquals(cat3ItemNames.size(), 1);

        List cat3SubCats = cat3.getSubCategories();
        Assert.assertEquals(cat3SubCats.size(), 0);

        Category cat4 = (Category) j.next();

        List cat4ItemNames = cat4.getItemNames();
        Assert.assertEquals(cat4ItemNames.size(), 1);

        List cat4SubCats = cat4.getSubCategories();
        Assert.assertEquals(cat4SubCats.size(), 0);
    }

    public void testJasperOutputs()
        throws Exception
    {
        PropertyManager.getInstance("net/sourceforge/gator/civil/configuration-test1.properties");
        ConfigurationFactory factory = ConfigurationFactoryBuilder.getInstance();

        Configuration configuration = factory.getConfiguration();
        ConfigurationImpl cec = (ConfigurationImpl) configuration;

        List outputs = cec.getOutputs();
        Assert.assertEquals(1, outputs.size());

        Iterator i = outputs.iterator();
        Output output = (Output) i.next();
        Assert.assertTrue(output instanceof JasperOutput);
        Assert.assertEquals("jasper report name", output.getName());
        Assert.assertEquals("Jasper Report Name", output.getDescription());

        JasperOutput jo = (JasperOutput) output;
        Set destinations = jo.getDestinations();
        Assert.assertEquals(1, destinations.size());

        Iterator j = destinations.iterator();
        JasperDestination dest = (JasperDestination) j.next();
        Assert.assertTrue(dest instanceof XlsJasperDestination);
    }
}
