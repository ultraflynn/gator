package net.sourceforge.gator;

import java.util.List;

import org.dom4j.Branch;

import net.sourceforge.gator.util.XMLSerializer;

public interface Estimate extends XMLSerializer
{
    Branch asXML(Branch parent);
    String getReference();
    void setReference(String reference);
    String getInternalReference();
    void setInternalReference(String internalReference);
    String getDate();
    void setDate(String date);
    String getCustomerName();
    void setCustomerName(String customerName);
    String getContactName();
    void setContactName(String contactName);
    String getCustomerAddress();
    void setCustomerAddress(String customerAddress);
    String getHomePhone();
    void setHomePhone(String homePhone);
    String getFax();
    void setFax(String fax);
    String getWorkPhone();
    void setWorkPhone(String workPhone);
    String getMobile();
    void setMobile(String mobile);
    String getNotes();
    void setNotes(String notes);
    String getJobCustomerName();
    void setJobCustomerName(String jobCustomerName);
    String getJobPhone();
    void setJobPhone(String jobPhone);
    String getJobAddress();
    void setJobAddress(String jobAddress);
    String getSiteContact();
    void setSiteContact(String siteContact);
    String getWorksRequired();
    void setWorksRequired(String worksRequired);
    String getJobNotes();
    void setJobNotes(String jobNotes);
    String getFurtherInstructions();
    void setFurtherInstructions(String furtherInstructions);
    List getJobs();
    void addJob(Job job);
    void removeJob(Job job);

    String toString();
}
