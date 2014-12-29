package net.sourceforge.gator.ui;

import net.sourceforge.gator.util.TraceableException;

public class UIException extends TraceableException
{
    public UIException()
    {
    }

    public UIException(String message)
    {
        super(message);
    }

    public UIException(Exception e)
    {
        super(e);
    }
}
