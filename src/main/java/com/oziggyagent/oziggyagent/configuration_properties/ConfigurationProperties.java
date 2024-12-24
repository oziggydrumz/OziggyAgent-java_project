package com.oziggyagent.oziggyagent.configuration_properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "ngrok")

public class ConfigurationProperties {
    private String basePath;

}
