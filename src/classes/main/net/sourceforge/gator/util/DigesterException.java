package net.sourceforge.gator.util;

public class DigesterException extends TraceableRuntimeException
{
    public DigesterException()
    {
        super();
    }

    public DigesterException(String message)
    {
        super(message);
    }

    public DigesterException(Throwable t)
    {
        super(t);
    }

    public DigesterException(Throwable t, String message)
    {
        super(t, message);
    }
}
