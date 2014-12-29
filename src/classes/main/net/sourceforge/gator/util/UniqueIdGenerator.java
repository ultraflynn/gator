package net.sourceforge.gator.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class UniqueIdGenerator
{
    private static int nextId = 0;

    public static synchronized String get()
    {
        nextId++;
        return Integer.toString(nextId);
    }

    public static synchronized String getTimestamp()
    {
        StringBuffer sb = new StringBuffer();
        Calendar calendar = new GregorianCalendar();

        sb.append(calendar.get(Calendar.YEAR));
        sb.append(calendar.get(Calendar.MONTH));
        sb.append(calendar.get(Calendar.DAY_OF_MONTH));
        sb.append(calendar.get(Calendar.HOUR));
        sb.append(calendar.get(Calendar.MINUTE));
        sb.append(calendar.get(Calendar.SECOND));

        return sb.toString();
    }
}
