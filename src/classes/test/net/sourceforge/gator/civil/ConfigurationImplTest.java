package net.sourceforge.gator.civil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.easymock.EasyMock;
import org.easymock.MockControl;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.Helper;
import net.sourceforge.gator.Item;
import net.sourceforge.gator.InvalidConfigurationException;
import net.sourceforge.gator.Output;

public class ConfigurationImplTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public ConfigurationImplTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(ConfigurationImplTest.class);
    }

    private ConfigurationImpl config;
    private MockControl categoryControl;
    private Category mockCategory;
    private MockControl helperControl;
    private Helper mockHelper;
    private MockControl fieldControl;
    private Field mockField;
    private MockControl itemControl;
    private Item mockItem;
    private MockControl outputControl;
    private Output mockOutput;

    public void setUp()
        throws Exception
    {
        config = new ConfigurationImpl();

        createMockObjects();
    }

    public void testCategories()
        throws Exception
    {
        mockCategory.setConfiguration(config);
        mockCategory.init();
        mockCategory.getName();
        categoryControl.setReturnValue("categoryname");

        activateMockObjects();

        config.addCategory(mockCategory);
        config.init();

        List categories = config.getCategories();
        Assert.assertEquals(1, categories.size());

        Iterator i = categories.iterator();
        Category category = (Category) i.next();

        Assert.assertEquals("categoryname", category.getName());

        verifyMockObjects();
    }

    public void testHelpers()
        throws Exception
    {
        mockHelper.setConfiguration(config);
        mockHelper.init();
        mockHelper.getName();
        helperControl.setReturnValue("helpername");

        activateMockObjects();

        config.addHelper(mockHelper);
        config.init();

        Set helperNames = config.getHelperNames();
        Assert.assertEquals(1, helperNames.size());

        Iterator i = helperNames.iterator();
        String helperName = (String) i.next();
        Helper helper = config.getHelper(helperName);

        Assert.assertEquals("helpername", helper.getName());

        verifyMockObjects();
    }

    public void testFields()
        throws Exception
    {
        mockField.setConfiguration(config);
        mockField.init();
        mockField.getName();
        fieldControl.setReturnValue("fieldname");

        activateMockObjects();

        config.addField(mockField);
        config.init();

        Set fieldNames = config.getFieldNames();
        Assert.assertEquals(1, fieldNames.size());

        Iterator i = fieldNames.iterator();
        String fieldName = (String) i.next();
        Field field = config.getField(fieldName);

        Assert.assertEquals("fieldname", field.getName());

        verifyMockObjects();
    }

    public void testItems()
        throws Exception
    {
        mockItem.setConfiguration(config);
        mockItem.init();
        mockItem.getName();
        itemControl.setReturnValue("itemname");

        activateMockObjects();

        config.addItem(mockItem);
        config.init();

        Set itemNames = config.getItemNames();
        Assert.assertEquals(1, itemNames.size());

        Iterator i = itemNames.iterator();
        String itemName = (String) i.next();
        Item item = config.getItem(itemName);

        Assert.assertEquals("itemname", item.getName());

        verifyMockObjects();
    }

    public void testOutputs()
        throws Exception
    {
        mockOutput.setConfiguration(config);
        mockOutput.init();
        mockOutput.getName();
        outputControl.setReturnValue("outputname");

        activateMockObjects();

        config.addOutput(mockOutput);
        config.init();

        List outputs = config.getOutputs();
        Assert.assertEquals(1, outputs.size());

        Iterator i = outputs.iterator();
        Output output = (Output) i.next();

        Assert.assertEquals("outputname", output.getName());

        verifyMockObjects();
    }

    public void testAsXMLNoObjects()
        throws Exception
    {
        activateMockObjects();

        Document document = DocumentHelper.createDocument();
        Branch xml = config.asXML(document);

        Document match = DocumentHelper.createDocument();
        Element cec = match.addElement("civilEngineeringConfiguration");
        cec.addAttribute("estimateDirectory", "");
        cec.addAttribute("estimatePrefix", "");
        cec.addAttribute("estimateSuffix", "");
        cec.addAttribute("referenceLength", "0");
        Element outputs = cec.addElement("outputs");
        outputs.addAttribute("directory", "");
        cec.addElement("helpers");
        cec.addElement("fields");
        Element categories = cec.addElement("categories");
        categories.addAttribute("rootCategoryName", "");
        cec.addElement("chargeableItems");

        Assert.assertEquals(cec.asXML(), xml.asXML());

        verifyMockObjects();
    }

    public void testInvalidItemName()
        throws Exception
    {
        try {
            config.getItem("DOESNT_EXIST");
            fail("Should have caught an exception");
        } catch (InvalidConfigurationException e) {
            Assert.assertEquals("[CONFIG] Could not find item: DOESNT_EXIST", e.getMessage());
        }
    }

    private void createMockObjects()
    {
        categoryControl = EasyMock.controlFor(Category.class);
        mockCategory = (Category) categoryControl.getMock();

        helperControl = EasyMock.controlFor(Helper.class);
        mockHelper = (Helper) helperControl.getMock();

        fieldControl = EasyMock.controlFor(Field.class);
        mockField = (Field) fieldControl.getMock();

        itemControl = EasyMock.controlFor(Item.class);
        mockItem = (Item) itemControl.getMock();

        outputControl = EasyMock.controlFor(Output.class);
        mockOutput = (Output) outputControl.getMock();
    }

    private void activateMockObjects()
    {
        categoryControl.activate();
        helperControl.activate();
        fieldControl.activate();
        itemControl.activate();
        outputControl.activate();
    }

    private void verifyMockObjects()
    {
        categoryControl.verify();
        helperControl.verify();
        fieldControl.verify();
        itemControl.verify();
        outputControl.verify();
    }
}
