package net.sourceforge.gator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HelperBundle
{
    private Configuration config;
    private List helpers;

    public HelperBundle(Configuration config)
    {
        this.config = config;

        helpers = new ArrayList();
    }

    public List getHelpers()
    {
        return helpers;
    }

    public void addHelper(Helper helper)
    {
        helpers.add(helper);
    }

    public void addHelperRef(String helperRef)
        throws ConfigurationException
    {
        Helper helper = config.getHelper(helperRef);

        if (helper == null) {
            throw new ConfigurationException("Could not find helper: " + helperRef);
        }

        helpers.add(helper);
    }

    public String toString()
    {
        boolean isFirst = true;

        StringBuffer sb = new StringBuffer();

        for (Iterator i = helpers.iterator(); i.hasNext();) {
            Helper helper = (Helper) i.next();

            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }

            sb.append(helper);
        }

        return sb.toString();
    }

    public boolean equals(Object o)
    {
         if (o == null) {
             return false;
         }

         if (!(o instanceof HelperBundle)) {
             return false;
         }

         HelperBundle hb = (HelperBundle) o;

         String thisString = toString();
         String hbString = hb.toString();

         return thisString.equals(hbString);
    }

    public int hashCode()
    {
        int bundleHashCode = toString().hashCode();

        return bundleHashCode;
    }
}
