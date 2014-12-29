package net.sourceforge.gator;

import net.sourceforge.gator.util.TraceableException;

public class EstimationException extends TraceableException
{
    public EstimationException()
    {
        super();
    }

    public EstimationException(String message)
    {
        super(message);
    }

    public EstimationException(Throwable t)
    {
        super(t);
    }

    public EstimationException(Throwable t, String message)
    {
        super(t, message);
    }
}
