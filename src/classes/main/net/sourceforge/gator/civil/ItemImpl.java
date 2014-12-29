package net.sourceforge.gator.civil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.Helper;
import net.sourceforge.gator.HelperBundle;
import net.sourceforge.gator.Item;

public class ItemImpl implements Item
{
    private Configuration configuration;
    private String name = "";
    private String description = "";
    private Float cost = new Float(0);
    private List helperRefs;
    private List helpers;
    private String fieldRef = "";
    private Field field;
    private Category category;

    public ItemImpl()
    {
        helperRefs = new ArrayList();
        helpers = new ArrayList();
    }

    public void init()
    {
        for (Iterator i = helperRefs.iterator(); i.hasNext();) {
            String helperRef = (String) i.next();

            Helper helper = configuration.getHelper(helperRef);
            helpers.add(helper);
        }

        field = configuration.getField(fieldRef);
    }

    public Branch asXML(Branch parent)
    {
        Element item = parent.addElement("item");

        item.addAttribute("name", name);
        item.addAttribute("description", description);

        Element helpersXML = item.addElement("helpers");
        for (Iterator i = helperRefs.iterator(); i.hasNext();) {
            String helperRef = (String) i.next();

            Element helper = helpersXML.addElement("helper");
            helper.addText(helperRef);
        }

        Element fieldElement = item.addElement("field");
        fieldElement.addText(fieldRef);

        Element costElement = item.addElement("cost");
        String convertedCost = cost.toString();
        costElement.addText(convertedCost);

        return item;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Float getCost()
    {
        return cost;
    }

    public void setCost(String cost)
    {
        this.cost = new Float(cost);
    }

    public List getHelpers()
    {
        return helpers;
    }

    public void addHelperRef(String helperRef)
    {
        helperRefs.add(helperRef);

        if (configuration != null) {
            Helper helper = configuration.getHelper(helperRef);

            if (helper != null) {
                helpers.add(helper);
            }
        }
    }

    public void setHelpers(HelperBundle helperBundle)
    {
        helperRefs = new ArrayList();
        helpers = new ArrayList();

        List newHelpers = helperBundle.getHelpers();

        for (Iterator i = newHelpers.iterator(); i.hasNext();) {
            Helper helper = (Helper) i.next();
            helperRefs.add(helper.getName());
            helpers.add(helper);
        }
    }

    public void removeHelperRef(String helperRef)
    {
        helperRefs.remove(helperRef);

        if (configuration != null) {
            Helper helper = configuration.getHelper(helperRef);

            if (helper != null) {
                helpers.remove(helper);
            }
        }
    }

    public Field getField()
    {
        return field;
    }

    public void setField(Field field)
    {
        this.fieldRef = field.getName();
        this.field = field;
    }

    public void setFieldRef(String fieldRef)
    {
        this.fieldRef = fieldRef;

        if (configuration != null) {
            field = configuration.getField(fieldRef);
        }
    }

    public String toString()
    {
        String fieldDescription = "";

        if (field != null) {
            fieldDescription = " [" + field + "]";
        } 

        return description + fieldDescription;
    }
}
