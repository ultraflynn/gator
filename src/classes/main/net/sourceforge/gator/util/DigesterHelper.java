package net.sourceforge.gator.util;

import java.net.MalformedURLException;
import java.net.URL;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ExtendedBaseRules;
import org.apache.commons.digester.RuleSet;

import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.digester.xmlrules.FromXmlRuleSet;

import org.xml.sax.SAXException;

public class DigesterHelper
{
    private Digester digester;

    public void setDigester(Digester digester)
    {
        this.digester = digester;
    }

    public Digester createDigester(String ruleFileName)
    {
        URL url = ResourceUtils.getURLFromResource(this, ruleFileName);
        digester = DigesterLoader.createDigester(url);

        return digester;
    }

    public Digester createExtendedDigester(String ruleFileName)
    {
        URL url = ResourceUtils.getURLFromResource(this, ruleFileName);
        RuleSet ruleSet = new FromXmlRuleSet(url);
        digester = new Digester();
        digester.setRules(new ExtendedBaseRules());
        digester.addRuleSet(ruleSet);

        return digester;
    }

    public Object parse(File file)
        throws DigesterException
    {
        Object instance = null;

        try {
            instance = digester.parse(file);
        } catch (SAXException e) {
            throw new DigesterException(e);
        } catch (IOException e) {
            throw new DigesterException(e);
        }

        return instance;
    }

    public Object parse(String fileFileName)
        throws DigesterException 
    {
        Object instance = null;

        if (digester == null) {
            throw new DigesterException("Please set a digester first");
        }

        try {
            InputStream is = ResourceUtils.getStreamFromResource(this, fileFileName);
            if (is == null) {
                throw new DigesterException("Could not load: " + fileFileName);
            }

            instance = digester.parse(is);
        } catch (MalformedURLException e) {
            throw new DigesterException(e);
        } catch (SAXException e) {
            throw new DigesterException(e);
        } catch (IOException e) {
            throw new DigesterException(e);
        }

        return instance;
    }

    public Object load(String ruleFileName, String fileFileName)
        throws DigesterException
    {
        createDigester(ruleFileName);
        return parse(fileFileName);
    }
}
