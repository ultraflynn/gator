package net.sourceforge.gator;

import java.util.ArrayList;
import java.util.List;

//import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import org.easymock.EasyMock;
import org.easymock.MockControl;

public class JasperOutputTest extends TestCase
{
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    public JasperOutputTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(JasperOutputTest.class);
    }

    public void testProcess()
        throws Exception
    {
        MockControl configControl = EasyMock.controlFor(Configuration.class);
        Configuration mockConfig = (Configuration) configControl.getMock();
        MockControl estimateControl = EasyMock.controlFor(Estimate.class);
        Estimate mockEstimate = (Estimate) estimateControl.getMock();
        MockControl jobControl = EasyMock.controlFor(Job.class);
        Job mockJob = (Job) jobControl.getMock();
        MockControl taskControl = EasyMock.controlFor(Task.class);
        Task mockTask = (Task) taskControl.getMock();
        MockControl subTaskControl = EasyMock.controlFor(SubTask.class);
        SubTask mockSubTask = (SubTask) subTaskControl.getMock();

        mockEstimate.getCustomerAddress();
        estimateControl.setReturnValue("aLine1\naLine2\naLine3\naLine4\naLine5\naLine6");
        mockEstimate.getJobAddress();
        estimateControl.setReturnValue("bLine1\nbLine2\nbLine3\nbLine4\nbLine5\nbLine6");
        mockEstimate.getCustomerName();
        estimateControl.setReturnValue("Test Customer");
        mockEstimate.getDate();
        estimateControl.setReturnValue("Date");
        mockEstimate.getInternalReference();
        estimateControl.setReturnValue("Int Ref");
        mockEstimate.getReference();
        estimateControl.setReturnValue("Reference");

        subTaskControl.activate();

        List subTasks = new ArrayList();
        subTasks.add(mockSubTask);

        mockTask.getSubTasks();
        taskControl.setReturnValue(subTasks);
        
        taskControl.activate();

        List tasks = new ArrayList();
        tasks.add(mockTask);

        mockJob.getTasks();
        jobControl.setReturnValue(tasks);

        jobControl.activate();

        List jobs = new ArrayList();
        jobs.add(mockJob);

        mockEstimate.getJobs();
        estimateControl.setReturnValue(jobs);

        mockConfig.getEstimatePrefix();
        configControl.setReturnValue("E");

        configControl.activate();
        estimateControl.activate();

        JasperOutput output = new JasperOutput();
        output.setConfiguration(mockConfig);
        output.setName("TestJasperReport");
        output.setDescription("Test Jasper Report");
        output.process(mockEstimate);

        estimateControl.verify();
        jobControl.verify();
        taskControl.verify();
        subTaskControl.verify();
        configControl.verify();
    }

    public void testProcessAnOutput()
        throws Exception
    {
        MockControl configControl = EasyMock.controlFor(Configuration.class);
        Configuration mockConfig = (Configuration) configControl.getMock();
        MockControl estimateControl = EasyMock.controlFor(Estimate.class);
        Estimate mockEstimate = (Estimate) estimateControl.getMock();
        MockControl jobControl = EasyMock.controlFor(Job.class);
        Job mockJob = (Job) jobControl.getMock();
        MockControl taskControl = EasyMock.controlFor(Task.class);
        Task mockTask = (Task) taskControl.getMock();
        MockControl subTaskControl = EasyMock.controlFor(SubTask.class);
        SubTask mockSubTask = (SubTask) subTaskControl.getMock();

        mockEstimate.getCustomerAddress();
        estimateControl.setReturnValue("aLine1\naLine2\naLine3\naLine4\naLine5\naLine6");
        mockEstimate.getJobAddress();
        estimateControl.setReturnValue("bLine1\nbLine2\nbLine3\nbLine4\nbLine5\nbLine6");
        mockEstimate.getCustomerName();
        estimateControl.setReturnValue("Test Customer");
        mockEstimate.getDate();
        estimateControl.setReturnValue("Date");
        mockEstimate.getInternalReference();
        estimateControl.setReturnValue("Int Ref");
        mockEstimate.getReference();
        estimateControl.setReturnValue("Reference");

        subTaskControl.activate();

        List subTasks = new ArrayList();
        subTasks.add(mockSubTask);

        mockTask.getSubTasks();
        taskControl.setReturnValue(subTasks);
        
        taskControl.activate();

        List tasks = new ArrayList();
        tasks.add(mockTask);

        mockJob.getTasks();
        jobControl.setReturnValue(tasks);

        jobControl.activate();

        List jobs = new ArrayList();
        jobs.add(mockJob);

        mockEstimate.getJobs();
        estimateControl.setReturnValue(jobs);

        mockConfig.getOutputDirectory();
        configControl.setReturnValue("build/tmp");
        mockConfig.getEstimatePrefix();
        configControl.setReturnValue("TEST");

        estimateControl.activate();
        configControl.activate();

        JasperOutput output = new JasperOutput();
        output.setName("TestJasperReport");
        output.setDescription("Test Jasper Report");

        XlsJasperDestination dest = new XlsJasperDestination();
        dest.setJasperOutput(output);
        output.setConfiguration(mockConfig);
        output.addDestination(dest);

        output.process(mockEstimate);

        estimateControl.verify();
        jobControl.verify();
        taskControl.verify();
        subTaskControl.verify();
        configControl.verify();
    }

    public void testProcessEstimateBreakdown()
        throws Exception
    {
        MockControl configControl = EasyMock.controlFor(Configuration.class);
        Configuration mockConfig = (Configuration) configControl.getMock();
        MockControl estimateControl = EasyMock.controlFor(Estimate.class);
        Estimate mockEstimate = (Estimate) estimateControl.getMock();
        MockControl jobControl = EasyMock.controlFor(Job.class);
        Job mockJob = (Job) jobControl.getMock();
        MockControl taskControl = EasyMock.controlFor(Task.class);
        Task mockTask = (Task) taskControl.getMock();
        MockControl subTaskControl = EasyMock.controlFor(SubTask.class);
        SubTask mockSubTask = (SubTask) subTaskControl.getMock();

        mockEstimate.getCustomerAddress();
        estimateControl.setReturnValue("aLine1\naLine2\naLine3\naLine4\naLine5\naLine6");
        mockEstimate.getJobAddress();
        estimateControl.setReturnValue("bLine1\nbLine2\nbLine3\nbLine4\nbLine5\nbLine6");
        mockEstimate.getCustomerName();
        estimateControl.setReturnValue("Test Customer");
        mockEstimate.getDate();
        estimateControl.setReturnValue("Date");
        mockEstimate.getInternalReference();
        estimateControl.setReturnValue("Int Ref");
        mockEstimate.getReference();
        estimateControl.setReturnValue("1234567");

        mockSubTask.getType();
        subTaskControl.setReturnValue("Type");
        mockSubTask.getDescription();
        subTaskControl.setReturnValue("Description");
        mockSubTask.getCode();
        subTaskControl.setReturnValue("Code");
        mockSubTask.getQuantityUnit();
        subTaskControl.setReturnValue("m");
        mockSubTask.getQuantity();
        subTaskControl.setReturnValue(new Float(67));
        mockSubTask.getUnitCost();
        subTaskControl.setReturnValue(new Float(78));
        mockSubTask.getTotalCost();
        subTaskControl.setReturnValue(new Float(89));
        mockSubTask.getUniqueId();
        subTaskControl.setReturnValue("a");

        subTaskControl.activate();

        List subTasks = new ArrayList();
        subTasks.add(mockSubTask);

        mockTask.getSubTasks();
        taskControl.setReturnValue(subTasks);
        mockTask.getPrefix();
        taskControl.setReturnValue("A");
        mockTask.getId();
        taskControl.setReturnValue(new Integer(5));
        mockTask.getJobDescription();
        taskControl.setReturnValue("Job Description Job Description Job Description Job Description Job Description");
        mockTask.getQuantityUnit();
        taskControl.setReturnValue("cm");
        mockTask.getQuantity();
        taskControl.setReturnValue(new Float(23));
        mockTask.getChargeRate();
        taskControl.setReturnValue(new Float(34));
        mockTask.getProfitMarginPercentage();
        taskControl.setReturnValue(new Float(45));
        mockTask.getCharge();
        taskControl.setReturnValue(new Float(56));
        mockTask.getUniqueId();
        taskControl.setReturnValue("b");
        mockTask.getTotalCost();
        taskControl.setReturnValue(new Float(123));
        
        taskControl.activate();

        List tasks = new ArrayList();
        tasks.add(mockTask);

        mockJob.getTasks();
        jobControl.setReturnValue(tasks);
        mockJob.getDescription();
        jobControl.setReturnValue("Preparation Works");
        mockJob.getQuantityUnit();
        jobControl.setReturnValue("m³");
        mockJob.getQuantity();
        jobControl.setReturnValue(new Float(12));
        mockJob.getUniqueId();
        jobControl.setReturnValue("c");
        mockJob.getQuantityPrefix();
        jobControl.setReturnValue("approx. area");

        jobControl.activate();

        List jobs = new ArrayList();
        jobs.add(mockJob);

        mockEstimate.getJobs();
        estimateControl.setReturnValue(jobs);

        mockConfig.getOutputDirectory();
        configControl.setReturnValue("build/tmp");
        mockConfig.getEstimatePrefix();
        configControl.setReturnValue("TEST");

        estimateControl.activate();
        configControl.activate();

        JasperOutput output = new JasperOutput();
        output.setName("EstimateBreakdown");
        output.setDescription("Test Estimate Breakdown");

        XlsJasperDestination dest = new XlsJasperDestination();
        dest.setJasperOutput(output);
        output.setConfiguration(mockConfig);
        output.addDestination(dest);

        output.process(mockEstimate);

        estimateControl.verify();
        jobControl.verify();
        taskControl.verify();
        subTaskControl.verify();
        configControl.verify();
    }
}
