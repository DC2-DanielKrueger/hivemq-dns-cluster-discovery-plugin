package com.hivemq.plugin.configuration;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Reloadable;


@Config.Sources("file:${configFile}")
@Config.HotReload(type= Config.HotReloadType.SYNC)
public interface DnsDiscoveryConfig extends Config, Reloadable {

    @Key("discoveryAddress")
    String discoveryAddress();

    @Key("resolutionTimeout")
    @DefaultValue("30")
    String resolutionTimeout();
}
