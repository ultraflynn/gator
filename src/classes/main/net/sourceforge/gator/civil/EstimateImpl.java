package net.sourceforge.gator.civil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Branch;
import org.dom4j.Element;

import net.sourceforge.gator.Estimate;
import net.sourceforge.gator.Job;

public class EstimateImpl implements Estimate
{
    private String reference = "";
    private String internalReference = "";
    private String date = "";
    private String customerName = "";
    private String contactName = "";
    private String customerAddress = "";
    private String homePhone = "";
    private String fax = "";
    private String workPhone = "";
    private String mobile = "";
    private String notes = "";
    private String jobCustomerName = "";
    private String jobPhone = "";
    private String jobAddress = "";
    private String siteContact = "";
    private String worksRequired = "";
    private String jobNotes = "";
    private String furtherInstructions = "";
    private List jobs;

    public EstimateImpl()
    {
        jobs = new ArrayList();
    }

    public Branch asXML(Branch parent)
    {
        Element root = parent.addElement("civilEngineeringEstimate");
        root.addAttribute("reference", reference);
        root.addAttribute("internalReference", internalReference);
        root.addAttribute("date", date);

        Element customer = root.addElement("customer");
        Element customerDetails = customer.addElement("customerDetails");

        Element cdCustomerName = customerDetails.addElement("customerName");
        cdCustomerName.addText(customerName);

        Element cdContact = customerDetails.addElement("contact");
        cdContact.addText(contactName);

        Element cdAddress = customerDetails.addElement("address");
        cdAddress.addText(customerAddress);

        Element cdHomePhone = customerDetails.addElement("homePhone");
        cdHomePhone.addText(homePhone);

        Element cdFax = customerDetails.addElement("fax");
        cdFax.addText(fax);

        Element cdWorkPhone = customerDetails.addElement("workPhone");
        cdWorkPhone.addText(workPhone);

        Element cdMobile = customerDetails.addElement("mobile");
        cdMobile.addText(mobile);

        Element cdNotes = customerDetails.addElement("notes");
        cdNotes.addText(notes);

        Element jobDetails = customer.addElement("jobDetails");

        Element jdCustomerName = jobDetails.addElement("customerName");
        jdCustomerName.addText(jobCustomerName);

        Element jdJobPhone = jobDetails.addElement("jobPhone");
        jdJobPhone.addText(jobPhone);

        Element jdAddress = jobDetails.addElement("address");
        jdAddress.addText(jobAddress);

        Element jdSiteContact = jobDetails.addElement("siteContact");
        jdSiteContact.addText(siteContact);

        Element jobSummary = root.addElement("jobSummary");

        Element jsWorksRequired = jobSummary.addElement("worksRequired");
        jsWorksRequired.addText(worksRequired);

        Element jsJobNotes = jobSummary.addElement("jobNotes");
        jsJobNotes.addText(jobNotes);

        Element jsFurtherInstructions = jobSummary.addElement("furtherInstructions");
        jsFurtherInstructions.addText(furtherInstructions);

        Element estimateBreakdown = root.addElement("estimateBreakdown");
        for (Iterator i = jobs.iterator(); i.hasNext();) {
            Job job = (Job) i.next();
            job.asXML(estimateBreakdown);
        }

        return root;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getInternalReference()
    {
        return internalReference;
    }

    public void setInternalReference(String internalReference)
    {
        this.internalReference = internalReference;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getCustomerAddress()
    {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress)
    {
        this.customerAddress = customerAddress;
    }

    public String getHomePhone()
    {
        return homePhone;
    }

    public void setHomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getWorkPhone()
    {
        return workPhone;
    }

    public void setWorkPhone(String workPhone)
    {
        this.workPhone = workPhone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getJobCustomerName()
    {
        return jobCustomerName;
    }

    public void setJobCustomerName(String jobCustomerName)
    {
        this.jobCustomerName = jobCustomerName;
    }

    public String getJobPhone()
    {
        return jobPhone;
    }

    public void setJobPhone(String jobPhone)
    {
        this.jobPhone = jobPhone;
    }

    public String getJobAddress()
    {
        return jobAddress;
    }

    public void setJobAddress(String jobAddress)
    {
        this.jobAddress = jobAddress;
    }

    public String getSiteContact()
    {
        return siteContact;
    }

    public void setSiteContact(String siteContact)
    {
        this.siteContact = siteContact;
    }

    public String getWorksRequired()
    {
        return worksRequired;
    }

    public void setWorksRequired(String worksRequired)
    {
        this.worksRequired = worksRequired;
    }

    public String getJobNotes()
    {
        return jobNotes;
    }

    public void setJobNotes(String jobNotes)
    {
        this.jobNotes = jobNotes;
    }

    public String getFurtherInstructions()
    {
        return furtherInstructions;
    }

    public void setFurtherInstructions(String furtherInstructions)
    {
        this.furtherInstructions = furtherInstructions;
    }

    public List getJobs()
    {
        return jobs;
    }

    public void addJob(Job job)
    {
        job.setEstimate(this);
        jobs.add(job);
    }

    public void removeJob(Job job)
    {
        jobs.remove(job);
    }

    public String toString()
    {
        return reference;
    }
}
