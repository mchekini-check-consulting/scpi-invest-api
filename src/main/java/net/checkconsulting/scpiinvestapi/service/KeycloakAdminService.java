package net.checkconsulting.scpiinvestapi.service;

import jakarta.annotation.PostConstruct;
import net.checkconsulting.scpiinvestapi.configuration.KeycloakConfiguration;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakAdminService {

    private final UserService userService;
    private Keycloak keycloak;
    private final KeycloakConfiguration keycloakConfiguration;

    public KeycloakAdminService(UserService userService, KeycloakConfiguration keycloakConfiguration) {
        this.userService = userService;
        this.keycloakConfiguration = keycloakConfiguration;
    }

    @PostConstruct
    public void init() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakConfiguration.getServerUrl())
                .realm(keycloakConfiguration.getRealm())
                .clientId(keycloakConfiguration.getClientId())
                .username(keycloakConfiguration.getAdminUsername())
                .password(keycloakConfiguration.getAdminPassword())
                .build();

    }

    public void updateRoleForUser(String newRoleName) {

        RoleRepresentation newRole = keycloak.realm(keycloakConfiguration.getRealm()).roles().get(newRoleName).toRepresentation();
        List<RoleRepresentation> currentRoles = keycloak.realm(keycloakConfiguration.getRealm()).users().get(userService.getUserId()).roles().realmLevel().listAll();
        keycloak.realm(keycloakConfiguration.getRealm()).users().get(userService.getUserId()).roles().realmLevel().remove(currentRoles);
        keycloak.realm(keycloakConfiguration.getRealm()).users().get(userService.getUserId()).roles().realmLevel().add(List.of(newRole));

    }

    public String getFirstNamByEmail(String realm, String email){
        return keycloak.realm(realm).users().search(email, 0, 1).get(0).getFirstName();
    }

    public String getLastNamByEmail(String realm, String email){
        return keycloak.realm(realm).users().search(email, 0, 1).get(0).getLastName();
    }
}

