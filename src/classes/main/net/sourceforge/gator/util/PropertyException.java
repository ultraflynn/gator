package net.sourceforge.gator.util;

public class PropertyException extends TraceableException
{
    public PropertyException()
    {
        super();
    }

    public PropertyException(String message)
    {
        super(message);
    }

    public PropertyException(Throwable t)
    {
        super(t);
    }

    public PropertyException(Throwable t, String message)
    {
        super(t, message);
    }
}
