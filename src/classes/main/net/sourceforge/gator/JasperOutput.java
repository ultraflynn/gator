package net.sourceforge.gator;

import java.io.InputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dori.jasper.engine.JasperManager;
import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JasperReport;
import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;

import dori.jasper.engine.data.JRBeanCollectionDataSource;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.util.ResourceUtils;

public class JasperOutput implements Output
{
    public static final String JASPER_RESOURCE_PATH = "jasper/";
    public static final String JASPER_RESOURCE_EXTN = ".jasper";
    public static final String ADDRESS_NEW_LINE = "\n";
    public static final String ADDRESS_SEPARATOR = ", ";

    private Configuration config;
    private String name;
    private String description;
    private Set destinations;

    public JasperOutput()
    {
        destinations = new HashSet();
    }

    public Branch asXML(Branch parent)
    {
        Element jrOutput = parent.addElement("jasperOutput");
        jrOutput.addAttribute("name", name);
        jrOutput.addAttribute("description", description);

        return jrOutput;
    }

    public void init()
    {
    }

    public void addDestination(JasperDestination dest)
    {
        dest.setConfiguration(config);
        dest.setJasperOutput(this);
        destinations.add(dest);
    }

    public Set getDestinations()
    {
        return destinations;
    }

    public void setConfiguration(Configuration config)
    {
        this.config = config;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void process(Estimate estimate)
        throws EstimationException
    {
        try {
            String resource = JASPER_RESOURCE_PATH + name + JASPER_RESOURCE_EXTN;
            InputStream is = ResourceUtils.getStreamFromResource(this, resource);

            if (is == null) {
                throw new EstimationException("Could not load report resource: " + JASPER_RESOURCE_PATH + name);
            }

            JasperReport report = JasperManager.loadReport(is);

            Map parameters = getParameters(estimate);
            JRDataSource dataSource = getDataSource(estimate);

            JasperPrint print = JasperManager.fillReport(report, parameters, dataSource);

            for (Iterator i = destinations.iterator(); i.hasNext();) {
                JasperDestination dest = (JasperDestination) i.next();
                dest.process(estimate, print);
            }
        } catch (JRException e) {
            throw new EstimationException(e);
        }
    }

    private Map getParameters(Estimate estimate)
    {
        Map parameters = new DefaultableHashMap();

        String customerAddress = estimate.getCustomerAddress();
        addAddress(customerAddress, parameters);

        String jobAddress = estimate.getJobAddress();
        jobAddress = concatAddress(jobAddress); 

        parameters.put("CustomerName", estimate.getCustomerName());
        parameters.put("JobAddress", jobAddress);
        parameters.put("Date", estimate.getDate());
        parameters.put("InternalReference", estimate.getInternalReference());
        parameters.put("Reference", config.getEstimatePrefix() + estimate.getReference());

        return parameters;
    }

    private String concatAddress(String address)
    {
        String[] addressLines = address.split(ADDRESS_NEW_LINE);
        String newAddress = "";

        int noOfLines = addressLines.length;
        for (int i = 0; i < noOfLines; i++) {
            newAddress += addressLines[i];
            if (i < (noOfLines - 1)) {
                newAddress += ADDRESS_SEPARATOR;
            }
        }
        
        return newAddress;
    }

    private void addAddress(String address, Map parameters)
    {
        String[] addressLines = address.split(ADDRESS_NEW_LINE);

        int noOfLines = addressLines.length;
        for (int i = 0; i < noOfLines; i++) {
            switch (i) {
                case 0:
                    parameters.put("CustomerAddress1", addressLines[0]);
                case 1:
                    parameters.put("CustomerAddress2", addressLines[1]);
                case 2:
                    parameters.put("CustomerAddress3", addressLines[2]);
                default:
                    String rest = join(addressLines, 3, noOfLines - 1);
                    parameters.put("CustomerAddress4", rest);
            }
        }
    }

    private String join(String[] array, int start, int end)
    {
        String joined = "";

        if (start > end) {
            throw new IllegalArgumentException();
        } else if (end > array.length) {
            throw new IllegalArgumentException();
        } else if (start > array.length) {
            throw new IllegalArgumentException();
        }

        for (int i = start; i <= end; i++) {
            if (i > start) {
                joined += ADDRESS_SEPARATOR;
            }

            joined += array[i];
        }

        return joined;
    }

    private JRDataSource getDataSource(Estimate estimate)
        throws EstimationException
    {
        Collection data = new HashSet();

        List jobs = estimate.getJobs();
        for (Iterator i = jobs.iterator(); i.hasNext();) {
            Job job = (Job) i.next();

            List tasks = job.getTasks();
            for (Iterator j = tasks.iterator(); j.hasNext();) {
                Task task = (Task) j.next();

                List subTasks = task.getSubTasks();
                for (Iterator k = subTasks.iterator(); k.hasNext();) {
                    SubTask subTask = (SubTask) k.next();

                    RowFacadeBean bean = new RowFacadeBean(job, task, subTask);

                    data.add(bean);
                }
            }
        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(data);

        return dataSource;
    }

    private class DefaultableHashMap extends HashMap
    {
        public Object get(Object key)
        {
            // FIXME Make sure this is doing something
            Object value = super.get(key);

            if (value == null) {
                value = "";
            }

            return value;
        }
    }
}
