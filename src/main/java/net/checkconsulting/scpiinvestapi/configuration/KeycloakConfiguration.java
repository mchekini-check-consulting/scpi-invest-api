package net.checkconsulting.scpiinvestapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Data
public class KeycloakConfiguration {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String adminUsername;
    private String adminPassword;
}
