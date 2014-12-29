package net.sourceforge.gator;

import net.sourceforge.gator.util.PropertyManager;

public class ConfigurationFactoryBuilder
{
    private static final String IMPL_CLASS_PROPERTY = "net.sourceforge.gator.ConfigurationFactoryClass";

    private static ConfigurationFactory instance;

    public static synchronized ConfigurationFactory getInstance()
        throws ConfigurationException
    {
        try {
            if (instance == null) {
                PropertyManager pm = PropertyManager.getInstance();
                String className = pm.get(IMPL_CLASS_PROPERTY);
                Class clazz = Class.forName(className);
                instance = (ConfigurationFactory) clazz.newInstance();
            }
            
            return instance;
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }
}

