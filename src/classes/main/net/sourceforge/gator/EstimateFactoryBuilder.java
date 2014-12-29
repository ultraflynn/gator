package net.sourceforge.gator;

import net.sourceforge.gator.util.PropertyManager;

public class EstimateFactoryBuilder
{
    private static final String IMPL_CLASS_PROPERTY = "net.sourceforge.gator.EstimateFactoryClass";

    private static EstimateFactory instance;

    public static synchronized EstimateFactory getInstance()
        throws ConfigurationException
    {
        try {
            if (instance == null) {
                PropertyManager pm = PropertyManager.getInstance();
                String className = pm.get(IMPL_CLASS_PROPERTY);
                Class clazz = Class.forName(className);
                instance = (EstimateFactory) clazz.newInstance();
            }
            
            return instance;
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }
}

