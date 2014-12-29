package net.sourceforge.gator;

public interface ConfigurationFactory
{
    Configuration getConfiguration()
        throws ConfigurationException;
    void saveConfiguration(Configuration config)
        throws ConfigurationException;
}
