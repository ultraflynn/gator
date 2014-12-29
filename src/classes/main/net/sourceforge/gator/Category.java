package net.sourceforge.gator;

import java.util.List;

import net.sourceforge.gator.util.Initable;
import net.sourceforge.gator.util.XMLSerializer;

public interface Category extends Initable, XMLSerializer
{
    void setConfiguration(Configuration configuration);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    List getSubCategories();
    void addCategory(Category category);
    List getItemNames();
    void addItem(Item item);
    String toString();
    void removeSubCategory(Category category);
    void removeItem(Item item);
}
