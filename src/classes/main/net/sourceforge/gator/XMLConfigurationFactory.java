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
import net.sourceforge.gator.util.UniqueIdGenerator;

public class XMLConfigurationFactory implements ConfigurationFactory
{
    public static final String DIGESTER_RULES = "net/sourceforge/gator/config-digester-rules.xml";
    public static final String CONFIG_PROPERTY = "net.sourceforge.gator.ConfigFileName";
    public static final String XML_ENCODING = "net.sourceforge.gator.XMLEncoding";

    public Configuration getConfiguration()
        throws ConfigurationException
    {
        try {
            DigesterHelper helper = new DigesterHelper();

            Digester digester = helper.createExtendedDigester(DIGESTER_RULES);

            PropertyManager pm = PropertyManager.getInstance();
            String configFileName = pm.get(CONFIG_PROPERTY);

            File file = new File(configFileName);

            Configuration configuration = (Configuration) helper.parse(file);

            if (configuration == null) {
                throw new ConfigurationException("Could not load configuration file " + configFileName);
            }

            configuration.init();

            return configuration;
        } catch (PropertyException e) {
            throw new ConfigurationException(e);
        }
    }

    public void saveConfiguration(Configuration config)
        throws ConfigurationException
    {
        try {
            Document document = DocumentHelper.createDocument();
            Branch estimateXML = config.asXML(document); 

            PropertyManager pm = null;

            String configFileName = "";
            try {
                pm = PropertyManager.getInstance();
                configFileName = pm.get(CONFIG_PROPERTY);
            } catch (PropertyException e) {
                throw new ConfigurationException(e);
            }

            String xmlEncoding = "";
            try {
                xmlEncoding = pm.get(XML_ENCODING);
            } catch (PropertyException e) {
                xmlEncoding = "ISO-8859-1";
            }

            String timeStamp = UniqueIdGenerator.getTimestamp();

            File currentConfig = new File(configFileName);
            File backupConfig = new File(timeStamp + "-" + configFileName);
            currentConfig.renameTo(backupConfig);

            FileOutputStream stream = new FileOutputStream(configFileName);

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(xmlEncoding);

            XMLWriter writer = new XMLWriter(stream, format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }
}
