package net.sourceforge.gator.civil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.Category;
import net.sourceforge.gator.Configuration;
import net.sourceforge.gator.Item;

public class CategoryImpl implements Category
{
    private Configuration configuration;
    private String name = "";
    private String description = "";
    private List subCategories;
    private List itemReferences;
    private Map items;

    public CategoryImpl()
    {
        subCategories = new ArrayList();
        itemReferences = new ArrayList();
        items = new HashMap();
    }

    public void init()
    {
        for (Iterator i = subCategories.iterator(); i.hasNext();) {
            Category category = (Category) i.next();
            category.setConfiguration(configuration);
            category.init();
        }
        for (Iterator i = itemReferences.iterator(); i.hasNext();) {
            String itemRef = (String) i.next();

            Item item = configuration.getItem(itemRef);
            items.put(itemRef, item);
        }
    }

    public Branch asXML(Branch parent)
    {
        Element category = parent.addElement("category");

        for (Iterator i = subCategories.iterator(); i.hasNext();) {
            Category subCategory = (Category) i.next();
            Branch subCategoryXML = subCategory.asXML(category);
        }

        for (Iterator i = itemReferences.iterator(); i.hasNext();) {
            String itemRef = (String) i.next();

            Element item = category.addElement("item");
            item.addText(itemRef);
        }

        category.addAttribute("name", name);
        category.addAttribute("description", description);

        return category;
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

    public List getSubCategories()
    {
        return subCategories;
    }

    public void addCategory(Category category)
    {
        subCategories.add(category);
    }

    public List getItemNames()
    {
        return itemReferences;
    }

    public void addItemRef(String itemRef)
    {
        itemReferences.add(itemRef);
    }

    public void addItem(Item item)
    {
        String itemRef = item.getName();
        itemReferences.add(itemRef);
        items.put(itemRef, item);
    }

    public void removeSubCategory(Category category)
    {
        subCategories.remove(category);
    }

    public void removeItem(Item item)
    {
        itemReferences.remove(item.getName());
        items.remove(item);
    }

    public String toString()
    {
        return description;
    }
}
