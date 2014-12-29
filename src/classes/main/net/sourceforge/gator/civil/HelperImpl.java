package net.sourceforge.gator.civil;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.ConfigurationException;
import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Helper;
import net.sourceforge.gator.HelperUI;

public class HelperImpl implements Helper
{
    private Configuration configuration;
    private String name = "";
    private String description = "";
    private String uiClassName = "";
    private HelperUI uiClass;

    public void init()
    {
    }

    public Branch asXML(Branch parent)
    {
        Element helper = parent.addElement("helper");

        helper.addAttribute("name", name);
        helper.addAttribute("description", description);
        helper.addAttribute("uiClassName", uiClassName);

        return helper;
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

    public HelperUI getUIClass()
    {
        return uiClass;
    }

    public void setUiClassName(String className)
        throws ConfigurationException
    {
        try {
            uiClassName = className;
            Class c = Class.forName(className);
            uiClass = (HelperUI) c.newInstance();
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
