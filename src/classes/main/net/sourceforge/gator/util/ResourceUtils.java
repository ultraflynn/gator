package net.sourceforge.gator.util;

import java.io.InputStream;

import java.net.URL;

public class ResourceUtils
{
    public static URL getURLFromResource(String resourceName)
    {
        Class clazz = ResourceUtils.class;
        return getURLFromResource(clazz, resourceName);
    }

    public static URL getURLFromResource(Object instance, String resourceName)
    {
        Class clazz = instance.getClass();
        return getURLFromResource(clazz, resourceName);
    }

    public static URL getURLFromResource(Class clazz, String resourceName)
    {
        ClassLoader classLoader = clazz.getClassLoader();
        URL url = classLoader.getResource(resourceName);

        return url;
    }

    public static InputStream getStreamFromResource(String resourceName)
    {
        Class clazz = ResourceUtils.class;
        return getStreamFromResource(clazz, resourceName);
    }

    public static InputStream getStreamFromResource(Object instance, String resourceName)
    {
        Class clazz = instance.getClass();
        return getStreamFromResource(clazz, resourceName);
    }

    public static InputStream getStreamFromResource(Class clazz, String resourceName)
    {
        ClassLoader classLoader = clazz.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(resourceName);

        return is;
    }
}
