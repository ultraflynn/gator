package net.sourceforge.gator.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TraceableRuntimeException extends RuntimeException
{
    private Throwable nestedException;

    public TraceableRuntimeException()
    {
        super();
    }

    public TraceableRuntimeException(String message)
    {
        super(message);
    }

    public TraceableRuntimeException(Throwable e)
    {
        super(e.getMessage());
        nestedException = e;
    }

    public TraceableRuntimeException(Throwable e, String message)
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
