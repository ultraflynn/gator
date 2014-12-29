package net.sourceforge.gator;

import java.io.File;
import java.io.FilenameFilter;

import net.sourceforge.gator.util.PropertyException;
import net.sourceforge.gator.util.PropertyManager;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

public class EstimateFountain
{
    private static final String NEW_ESTIMATE_CLASS_PROP = "net.sourceforge.gator.NewEstimateClass";
    private static final String REFERENCE_PADDING_CHAR = "0";

    private static EstimateFountain instance;

    private Configuration configuration;

    public static synchronized EstimateFountain getInstance(Configuration configuration)
    {
        if (instance == null) {
            instance = new EstimateFountain();
        }

        instance.configuration = configuration;

        return instance;
    }

    protected EstimateFountain()
    {
    }

    public Estimate create()
        throws ConfigurationException
    {
        try {
            Estimate estimate = null;

            String directoryName = configuration.getEstimateDirectory();

            if (directoryName == null) {
                throw new ConfigurationException("Cannot create estimate as estimate directory not set");
            }

            File estimateDirectory = new File(directoryName);

            if (!estimateDirectory.exists()) {
                throw new ConfigurationException("Estimate directory " + directoryName + " does not exist");
            }
            if (!estimateDirectory.isDirectory()) {
                throw new ConfigurationException("Estimate directory " + directoryName + " is not a directory");
            }

            MaxEstimateReferenceFilter filter = new MaxEstimateReferenceFilter();

            estimateDirectory.list(filter);
            
            String reference = filter.getMaxReference();
            int referenceLength = configuration.getReferenceLength();
            if (referenceLength > 0) {
                reference = padReference(referenceLength, reference);
            }

            PropertyManager pm = PropertyManager.getInstance();
            String className = pm.get(NEW_ESTIMATE_CLASS_PROP);
            Class clazz = Class.forName(className);
            estimate = (Estimate) clazz.newInstance();

            estimate.setReference(reference);

            return estimate;
        } catch (PropertyException e) {
            throw new ConfigurationException(e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException(e);
        } catch (InstantiationException e) {
            throw new ConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException(e);
        } catch (RESyntaxException e) {
            throw new ConfigurationException(e);
        }
    }

    private String padReference(int referenceLength, String reference)
    {
        String padding = "";
        int len = reference.length();
        int padCharCnt = referenceLength - len;

        if (padCharCnt > 0) {
            for (int i = 0; i < padCharCnt; i++) {
                padding += REFERENCE_PADDING_CHAR;
            }
        }

        return padding + reference;
    }

    private class MaxEstimateReferenceFilter implements FilenameFilter
    {

        private int maxReference = 0;
        private RE re;

        public MaxEstimateReferenceFilter()
            throws RESyntaxException
        {
            re = new RE("^\\d+$");
        }

        public boolean accept(File dir, String name)
        {
            if (re.match(name)) {
                File f = new File(dir, name);

                if (f.isDirectory()) {
                    int lastChar = name.length();
                    String lastRef = name.substring(0, lastChar);
                    int ref = Integer.parseInt(lastRef.trim());

                    if (ref > maxReference) {
                        maxReference = ref;
                    }
                }
            }

            return true;
        }

        public String getMaxReference()
        {
            return Integer.toString(maxReference + 1);
        }
    }
}
