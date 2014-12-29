package net.sourceforge.gator;

import net.sourceforge.gator.util.TraceableRuntimeException;

public class InvalidConfigurationException extends TraceableRuntimeException
{
    public InvalidConfigurationException()
    {
        super();
    }

    public InvalidConfigurationException(String message)
    {
        super(message);
    }

    public InvalidConfigurationException(Throwable t)
    {
        super(t);
    }

    public InvalidConfigurationException(Throwable t, String message)
    {
        super(t, message);
    }
}
