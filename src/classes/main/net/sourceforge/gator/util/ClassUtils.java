package net.sourceforge.gator.util;

public class ClassUtils
{
    public static boolean isImplementor(Object obj, String interfaceName)
    {
        Class clazz = obj.getClass();
        Class[] interfaces = clazz.getInterfaces();

        for (int i = 0; i < interfaces.length; i++) {
            String name = interfaces[i].getName();

            if (name.equals(interfaceName)) {
                return true;
            }
        }

        return false;
    }

}
