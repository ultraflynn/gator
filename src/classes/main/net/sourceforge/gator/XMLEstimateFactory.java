package net.sourceforge.gator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.digester.Digester;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import net.sourceforge.gator.util.DigesterHelper;
import net.sourceforge.gator.util.PropertyException;
import net.sourceforge.gator.util.PropertyManager;

public class XMLEstimateFactory implements EstimateFactory
{
    public static final String DIGESTER_RULES = "net/sourceforge/gator/estimate-digester-rules.xml";
    public static final String XML_ENCODING = "net.sourceforge.gator.XMLEncoding";

    public Estimate getEstimate(String estimateFileName)
        throws EstimationException
    {
        DigesterHelper helper = new DigesterHelper();

        Digester digester = helper.createDigester(DIGESTER_RULES);

        File file = new File(estimateFileName);

        return (Estimate) helper.parse(file);
    }

    public void saveEstimate(Configuration config, Estimate estimate)
        throws EstimationException, ConfigurationException
    {
        try {
            if (estimate == null) {
                throw new IllegalStateException();
            }

            String estimateDirectory = config.getEstimateDirectory();
            String reference = estimate.getReference();

            File dir = new File(estimateDirectory, reference);

            if (!(dir.exists())) {
                dir.mkdir();
            }

            String estimatePrefix = config.getEstimatePrefix();
            String estimateSuffix = config.getEstimateSuffix();

            StringBuffer sb = new StringBuffer();
            sb.append(estimatePrefix);
            sb.append(reference);
            sb.append(estimateSuffix);

            File estimateFile = new File(dir, sb.toString());

            Document document = DocumentHelper.createDocument();
            Branch estimateXML = estimate.asXML(document); 

            PropertyManager pm = null;
            try {
                pm = PropertyManager.getInstance();
            } catch (PropertyException e) {
                throw new ConfigurationException(e);
            }

            String xmlEncoding = "";

            try {
                xmlEncoding = pm.get(XML_ENCODING);
            } catch (PropertyException e) {
                xmlEncoding = "ISO-8859-1";
            }

            FileOutputStream stream = new FileOutputStream(estimateFile.toString());

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(xmlEncoding);

            XMLWriter writer = new XMLWriter(stream, format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            throw new EstimationException(e);
        }
    }
}
