package net.sourceforge.gator;

import java.util.List;
import java.util.Set;

import org.dom4j.Branch;

import net.sourceforge.gator.util.Initable;
import net.sourceforge.gator.util.XMLSerializer;

public interface Configuration extends Initable, XMLSerializer
{
    String getEstimateDirectory();
    String getEstimatePrefix();
    String getEstimateSuffix();
    String getOutputDirectory();
    String getRootCategoryName();
    int getReferenceLength();
    Branch asXML(Branch parent);
    List getCategories();
    void addCategory(Category category);
    Set getHelperNames();
    Set getFieldNames();
    Set getItemNames();
    void addItem(Item item);
    Item getItem(String itemRef);
    Helper getHelper(String helperRef);
    Field getField(String fieldRef);
    List getOutputs();
    void removeCategory(Category category);
}
