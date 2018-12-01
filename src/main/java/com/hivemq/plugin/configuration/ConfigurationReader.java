package com.hivemq.plugin.configuration;


import com.hivemq.plugin.api.parameter.PluginInformation;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.event.ReloadEvent;
import org.aeonbits.owner.event.ReloadListener;

import java.io.File;

public class ConfigurationReader {


    public static final String CONFIG_PATH = "dnsdiscovery.properties";

    public ConfigurationReader(final PluginInformation pluginInformation) {
        //tell the AeonBitsOwner Factory to overwrite the symbol "rateLimitingConfigFile" with the actual path of the file.
        // This makes it possible to use the symbol as the file path and use the auto reload
        ConfigFactory.setProperty("configFile", new File(pluginInformation.getPluginHomeFolder(), CONFIG_PATH).toURI().getRawPath());
    }


    public DnsDiscoveryConfig get() {
        return ConfigCache.getOrCreate(DnsDiscoveryConfig.class);
    }


}
