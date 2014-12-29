package net.sourceforge.gator;

import java.util.HashMap;
import java.util.Map;

public class MapParameters implements Parameters
{
    private Map parameters;

    public MapParameters()
    {
        parameters = new HashMap();
    }

    public void add(String name, String parameter)
    {
        parameters.put(name, parameter);
    }

    public String get(String name)
    {
        return (String) parameters.get(name);
    }

    public void remove(String name)
    {
        parameters.remove(name);
    }
}
