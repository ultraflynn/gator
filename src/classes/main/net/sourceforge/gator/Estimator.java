package net.sourceforge.gator;

import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.gator.util.PropertyException;
import net.sourceforge.gator.util.PropertyManager;

public class Estimator
{
    public static final String JOB_CLASS_NAME_PROPERTY = "net.sourceforge.gator.JobClassName";
    public static final String TASK_CLASS_NAME_PROPERTY = "net.sourceforge.gator.TaskClassName";
    public static final String SUBTASK_CLASS_NAME_PROPERTY = "net.sourceforge.gator.SubTaskClassName";
    public static final String CATEGORY_CLASS_NAME_PROPERTY = "net.sourceforge.gator.CategoryClassName";
    public static final String ITEM_CLASS_NAME_PROPERTY = "net.sourceforge.gator.ItemClassName";

    private Configuration config;
    private ConfigurationFactory configFactory;
    private Estimate estimate;
    private EstimateFactory estimateFactory;
    private PropertyManager pm;
    private String jobClassName;
    private String taskClassName;
    private String subTaskClassName;
    private String categoryClassName;
    private String itemClassName;

    public Estimator()
        throws ConfigurationException
    {
        configFactory = ConfigurationFactoryBuilder.getInstance();
        config = configFactory.getConfiguration();

        estimateFactory = EstimateFactoryBuilder.getInstance();

        try {
            pm = PropertyManager.getInstance();
            jobClassName = pm.get(JOB_CLASS_NAME_PROPERTY);
            taskClassName = pm.get(TASK_CLASS_NAME_PROPERTY);
            subTaskClassName = pm.get(SUBTASK_CLASS_NAME_PROPERTY);
            categoryClassName = pm.get(CATEGORY_CLASS_NAME_PROPERTY);
            itemClassName = pm.get(ITEM_CLASS_NAME_PROPERTY);
        } catch (PropertyException e) {
            throw new ConfigurationException(e);
        }
    }

    public synchronized Estimate create()
        throws ConfigurationException
    {
        EstimateFountain fountain = EstimateFountain.getInstance(config);
        estimate = fountain.create();

        return estimate;
    }

    public synchronized Estimate load(String estimateFileName)
        throws EstimationException
    {
        String directory = config.getEstimateDirectory();
        File file = new File(directory, estimateFileName);
        estimate = estimateFactory.getEstimate(file.toString());

        return estimate;
    }

    public synchronized void save()
        throws EstimationException, ConfigurationException
    {
        if (estimate == null) {
            throw new IllegalStateException();
        }

        Calendar calendar = new GregorianCalendar();
        Date now = new Date();
        calendar.setTime(now);

        StringBuffer sb = new StringBuffer();
        sb.append(calendar.get(Calendar.DAY_OF_MONTH));
        sb.append(" ");
        sb.append(calendar.get(Calendar.MONTH));
        sb.append(", ");
        sb.append(calendar.get(Calendar.YEAR));

        estimate.setDate(sb.toString());
        estimateFactory.saveEstimate(config, estimate);
    }

    public Estimate get()
    {
        if (estimate == null) {
            throw new IllegalStateException();
        }

        return estimate;
    }

    public synchronized void print()
        throws EstimationException
    {
        if (estimate == null) {
            throw new IllegalStateException();
        }

        List outputs = config.getOutputs();
        for (Iterator i = outputs.iterator(); i.hasNext();) {
            Output output = (Output) i.next();
            output.process(estimate);
        }
    }

    public Configuration getConfiguration()
    {
        return config;
    }

    public synchronized void saveConfiguration()
        throws ConfigurationException
    {
        configFactory.saveConfiguration(config);
    }

    public Job createJob()
    {
        try {
            Class c = Class.forName(jobClassName);
            return (Job) c.newInstance();
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationException(e);
        } catch (InstantiationException e) {
            throw new InvalidConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new InvalidConfigurationException(e);
        }
    }

    public Task createTask()
    {
        try {
            Class c = Class.forName(taskClassName);
            return (Task) c.newInstance();
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationException(e);
        } catch (InstantiationException e) {
            throw new InvalidConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new InvalidConfigurationException(e);
        }
    }

    public SubTask createSubTask()
    {
        try {
            Class c = Class.forName(subTaskClassName);
            return (SubTask) c.newInstance();
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationException(e);
        } catch (InstantiationException e) {
            throw new InvalidConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new InvalidConfigurationException(e);
        }
    }

    public Category createCategory()
    {
        try {
            Class c = Class.forName(categoryClassName);
            return (Category) c.newInstance();
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationException(e);
        } catch (InstantiationException e) {
            throw new InvalidConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new InvalidConfigurationException(e);
        }
    }

    public Item createItem()
    {
        try {
            Class c = Class.forName(itemClassName);
            return (Item) c.newInstance();
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationException(e);
        } catch (InstantiationException e) {
            throw new InvalidConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new InvalidConfigurationException(e);
        }
    }
}
