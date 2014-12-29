package net.sourceforge.gator.util;

import java.io.InputStream;
import java.io.IOException;

import java.util.Properties;

public class PropertyManager
{
    private static final String DEFAULT_PROPERTY_RESOURCE = "net/sourceforge/gator/application.properties";

    private static PropertyManager instance;

    private String propertyResource;
    private Properties properties;

    public static synchronized PropertyManager getInstance()
        throws PropertyException
    {
        if (instance == null) {
            instance = new PropertyManager();
        }

        return instance;
    }

    public static synchronized PropertyManager getInstance(String propertyResource)
        throws PropertyException
    {
        if (instance == null) {
            instance = new PropertyManager(propertyResource);
        } else if (!instance.propertyResource.equals(propertyResource)) {
            instance.loadProperties(propertyResource);
        }

        return instance;
    }

    public static synchronized void useDefaultProperties()
        throws PropertyException
    {
        if (instance != null) {
            instance.loadProperties(DEFAULT_PROPERTY_RESOURCE);
        }
    }

    protected PropertyManager()
        throws PropertyException
    {
        loadProperties(DEFAULT_PROPERTY_RESOURCE);
    }

    protected PropertyManager(String propertyResource)
        throws PropertyException
    {
        loadProperties(propertyResource);
    }

    public String get(String key)
        throws PropertyException
    {
        String result = properties.getProperty(key);

        if (result == null) {
            throw new PropertyException("Property " + key + " not found");
        }

        return result;
    }

    private void loadProperties(String propertyResource)
        throws PropertyException
    {
        try {
            this.propertyResource = propertyResource;

            InputStream is = ResourceUtils.getStreamFromResource(propertyResource);
            if (is == null) {
                throw new PropertyException("Can't find property resource: " + propertyResource);
            }
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new PropertyException(e);
        }
    }
}
