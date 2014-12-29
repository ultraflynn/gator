package net.sourceforge.gator.civil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.Item;
import net.sourceforge.gator.InvalidConfigurationException;
import net.sourceforge.gator.Helper;
import net.sourceforge.gator.Output;

public class ConfigurationImpl implements Configuration
{
    private String estimateDirectory = "";
    private String estimatePrefix = "";
    private String estimateSuffix = "";
    private String outputDirectory = "";
    private String rootCategoryName = "";
    private int referenceLength;
    private List categories;
    private Map helpers;
    private Map fields;
    private Map items;
    private List outputs;

    public ConfigurationImpl()
    {
        categories = new ArrayList();
        helpers = new HashMap();
        fields = new HashMap();
        items = new HashMap();
        outputs = new ArrayList();
    }

    public void init()
    {
        for (Iterator i = categories.iterator(); i.hasNext();) {
            Category category = (Category) i.next();
            category.init();
        }
        for (Iterator i = helpers.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Helper helper = (Helper) entry.getValue();
            helper.init();
        }
        for (Iterator i = fields.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Field field = (Field) entry.getValue();
            field.init();
        }
        for (Iterator i = items.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Item item = (Item) entry.getValue();
            item.init();
        }
        for (Iterator i = outputs.iterator(); i.hasNext();) {
            Output output = (Output) i.next();
            output.init();
        }
    }

    public Branch asXML(Branch parent)
    {
        Element root = parent.addElement("civilEngineeringConfiguration");
        root.addAttribute("estimateDirectory", estimateDirectory);
        root.addAttribute("estimatePrefix", estimatePrefix);
        root.addAttribute("estimateSuffix", estimateSuffix);
        root.addAttribute("referenceLength", Integer.toString(referenceLength));

        Element outputsElement = root.addElement("outputs");
        outputsElement.addAttribute("directory", outputDirectory);
        for (Iterator i = outputs.iterator(); i.hasNext();) {
            Output output = (Output) i.next();
            output.asXML(outputsElement);
        }

        Element helpersElement = root.addElement("helpers");
        for (Iterator i = helpers.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Helper helper = (Helper) entry.getValue();
            helper.asXML(helpersElement);
        }

        Element fieldsElement = root.addElement("fields");
        for (Iterator i = fields.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Field field = (Field) entry.getValue();
            field.asXML(fieldsElement);
        }

        Element categoriesElement = root.addElement("categories");
        categoriesElement.addAttribute("rootCategoryName", rootCategoryName);
        for (Iterator i = categories.iterator(); i.hasNext();) {
            Category category = (Category) i.next();
            category.asXML(categoriesElement);
        }

        Element itemsElement = root.addElement("chargeableItems");
        for (Iterator i = items.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Item item = (Item) entry.getValue();
            item.asXML(itemsElement);
        }

        return root;
    }

    public String getEstimateDirectory()
    {
        return estimateDirectory;
    }

    public void setEstimateDirectory(String estimateDirectory)
    {
        this.estimateDirectory = estimateDirectory;
    }

    public String getEstimatePrefix()
    {
        return estimatePrefix;
    }

    public void setEstimatePrefix(String estimatePrefix)
    {
        this.estimatePrefix = estimatePrefix;
    }

    public String getEstimateSuffix()
    {
        return estimateSuffix;
    }

    public void setEstimateSuffix(String estimateSuffix)
    {
        this.estimateSuffix = estimateSuffix;
    }

    public int getReferenceLength()
    {
        return referenceLength;
    }

    public void setReferenceLength(int referenceLength)
    {
        this.referenceLength = referenceLength;
    }

    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }

    public String getRootCategoryName()
    {
        return rootCategoryName;
    }

    public void setRootCategoryName(String rootCategoryName)
    {
        this.rootCategoryName = rootCategoryName;
    }

    public List getCategories()
    {
        return categories;
    }

    public void addCategory(Category category)
    {
        category.setConfiguration(this);
        categories.add(category);
    }

    public Set getHelperNames()
    {
        return helpers.keySet();
    }

    public void addHelper(Helper helper)
    {
        helper.setConfiguration(this);
        helpers.put(helper.getName(), helper);
    }

    public Set getFieldNames()
    {
        return fields.keySet();
    }

    public void addField(Field field)
    {
        field.setConfiguration(this);
        fields.put(field.getName(), field);
    }

    public Set getItemNames()
    {
        return items.keySet();
    }

    public void addItem(Item item)
    {
        item.setConfiguration(this);
        items.put(item.getName(), item);
    }

    public Item getItem(String itemRef)
    {
        Item item = (Item) items.get(itemRef);

        if (item == null) {
            throw new InvalidConfigurationException("[CONFIG] Could not find item: " + itemRef);
        }
        
        return item;
    }

    public Helper getHelper(String helperRef)
    {
        return (Helper) helpers.get(helperRef);
    }

    public Field getField(String fieldRef)
    {
        return (Field) fields.get(fieldRef);
    }

    public void addOutput(Output output)
    {
        output.setConfiguration(this);
        outputs.add(output);
    }

    public List getOutputs()
    {
        return outputs;
    }

    public void removeCategory(Category category)
    {
        categories.remove(category);
    }
}
