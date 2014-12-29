package net.sourceforge.gator;

public interface EstimateFactory
{
    Estimate getEstimate(String estimateFileName)
        throws EstimationException;
    void saveEstimate(Configuration config, Estimate estimate)
        throws EstimationException, ConfigurationException;
}
