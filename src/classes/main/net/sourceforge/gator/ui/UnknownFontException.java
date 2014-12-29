package net.sourceforge.gator.ui;

import net.sourceforge.gator.util.TraceableException;

public class UnknownFontException extends TraceableException
{
    public UnknownFontException()
    {
    }

    public UnknownFontException(String message)
    {
        super(message);
    }

    public UnknownFontException(Exception e)
    {
        super(e);
    }
}

