package net.checkconsulting.scpiinvestapi.resources;

import net.checkconsulting.scpiinvestapi.service.KeycloakAdminService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserResource {

    private final KeycloakAdminService keycloakService;

    public UserResource(KeycloakAdminService keycloakService) {
        this.keycloakService = keycloakService;
    }


    @PutMapping
    public void updateRole(@RequestParam("newRole") String newRole) {
        keycloakService.updateRoleForUser(newRole);
    }
}
