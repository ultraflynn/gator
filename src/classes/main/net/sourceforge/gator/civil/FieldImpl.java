package net.sourceforge.gator.civil;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Field;
import net.sourceforge.gator.FieldUI;

public class FieldImpl implements Field
{
    private Configuration configuration;
    private String name = "";
    private String description = "";
    private String uiClassName = "";
    private FieldUI uiClass;

    public void init()
    {
    }

    public Branch asXML(Branch parent)
    {
        Element field = parent.addElement("field");

        field.addAttribute("name", name);
        field.addAttribute("description", description);
        field.addAttribute("uiClassName", uiClassName);

        return field;
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

    public FieldUI getUIClass()
    {
        return uiClass;
    }

    public void setUiClassName(String className)
        throws ConfigurationException
    {
        try {
            uiClassName = className;
            Class c = Class.forName(className);
            uiClass = (FieldUI) c.newInstance();
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException(e);
        } catch (InstantiationException e) {
            throw new ConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }

    public String toString()
    {
        return description;
    }
}
