package net.sourceforge.gator;

import net.sourceforge.gator.util.TraceableException;

public class ConfigurationException extends TraceableException
{
    public ConfigurationException()
    {
        super();
    }

    public ConfigurationException(String message)
    {
        super(message);
    }

    public ConfigurationException(Throwable t)
    {
        super(t);
    }

    public ConfigurationException(Throwable t, String message)
    {
        super(t, message);
    }
}
