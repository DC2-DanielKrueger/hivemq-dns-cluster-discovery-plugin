package com.hivemq.plugin.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps the AeonBitsOwner Config and makes it possible to use Environmental Variables
 * Adds some sanity checks
 */
public class DnsDiscoveryConfigExtended {

    private final DnsDiscoveryConfig aeonbitsOwnerConfig;

    private static final String DISCOVERY_ADDRESS_ENV = "HIVEMQ_DNS_DISCOVERY_ADDRESS";
    private static final String DISCOVERY_TIMEOUT_ENV = "HIVEMQ_DNS_DISCOVERY_TIMEOUT";
    private static final int DEFAULT_DISCOVERY_TIMEOUT = 30;

    private static final Logger log = LoggerFactory.getLogger(DnsDiscoveryConfigExtended.class);

    //todo disable aeonbitsowner if no file is present or we dont want to use it

    public DnsDiscoveryConfigExtended(ConfigurationReader reader){
        aeonbitsOwnerConfig = reader.get();
    }

    public String discoveryAddress() {
        String discoveryAddress =null;
        try {
            discoveryAddress = aeonbitsOwnerConfig.discoveryAddress();
        }catch(Exception e){
            //ignore
        }
        // if its not set lookup in System Environment
        if(discoveryAddress==null||discoveryAddress.isEmpty()){
            final String discoveryAddressEnv = System.getenv(DISCOVERY_ADDRESS_ENV);
            if (discoveryAddressEnv == null || discoveryAddressEnv.isEmpty()) {
                log.error("No discovery address was set in the configuration file or environment variable");
                return null;
            } else {
                return discoveryAddressEnv;
            }
        }
        return discoveryAddress;
    }

    public int resolutionTimeout() {
        String resolveTimeout =null;
        try {
            resolveTimeout = aeonbitsOwnerConfig.resolutionTimeout();
        }catch(Exception e){
            //ignore
        }

        if (resolveTimeout == null || resolveTimeout.isEmpty()) {
            resolveTimeout = System.getenv(DISCOVERY_TIMEOUT_ENV);
            if (resolveTimeout == null || resolveTimeout.isEmpty()) {
                log.warn("No DNS resolution timeout configured in configuration file or environment variable, using default: {}", DEFAULT_DISCOVERY_TIMEOUT);
                return DEFAULT_DISCOVERY_TIMEOUT;
            }
        }
        try {
            return Integer.parseInt(resolveTimeout);
        } catch (NumberFormatException e) {
            log.error("Invalid format {} for DNS discovery property resolutionTimeout, using default: {}", resolveTimeout, DEFAULT_DISCOVERY_TIMEOUT);
            return DEFAULT_DISCOVERY_TIMEOUT;
        }
    }
}
