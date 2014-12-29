package net.sourceforge.gator.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TraceableException extends Exception
{
    private Throwable nestedException;

    public TraceableException()
    {
        super();
    }

    public TraceableException(String message)
    {
        super(message);
    }

    public TraceableException(Throwable e)
    {
        super(e.getMessage());
        nestedException = e;
    }

    public TraceableException(Throwable e, String message)
    {
        super(message);
        nestedException = e;
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream ps)
    {
        printStackTrace(new PrintWriter(ps, true));
    }

    public void printStackTrace(PrintWriter pw)
    {
        if (nestedException != null) {
            pw.println("---- Nested Exception ----");
            nestedException.printStackTrace(pw);
            pw.println("--------------------------");
        }

        super.printStackTrace(pw);
    }

    public Throwable getNestedException()
    {
        return nestedException;
    }
}
