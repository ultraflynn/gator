package net.sourceforge.gator;

import net.sourceforge.gator.util.TraceableRuntimeException;

public class StateInstantiationException extends TraceableRuntimeException
{
    public StateInstantiationException()
    {
        super();
    }

    public StateInstantiationException(String message)
    {
        super(message);
    }

    public StateInstantiationException(Throwable t)
    {
        super(t);
    }

    public StateInstantiationException(Throwable t, String message)
    {
        super(t, message);
    }
}
