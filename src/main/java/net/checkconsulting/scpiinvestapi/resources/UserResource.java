package net.checkconsulting.scpiinvestapi.resources;

import net.checkconsulting.scpiinvestapi.dto.ProfileInformationDto;
import net.checkconsulting.scpiinvestapi.service.KeycloakAdminService;
import net.checkconsulting.scpiinvestapi.service.UserPrefenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserResource {

    private final KeycloakAdminService keycloakService;
    private final UserPrefenceService userPrefenceService;

    public UserResource(KeycloakAdminService keycloakService, UserPrefenceService userPrefenceService) {
        this.keycloakService = keycloakService;
        this.userPrefenceService = userPrefenceService;
    }


    @PutMapping
    public void updateRole(@RequestParam("newRole") String newRole) {
        keycloakService.updateRoleForUser(newRole);
    }

    @GetMapping("preference")
    public ProfileInformationDto getUserPreference(){
        return userPrefenceService.getUserPreference();
    }

    @PostMapping("/preference")
    public void createOrUpdateUserPreferences(@RequestBody ProfileInformationDto profileInformationDto){
        userPrefenceService.createOrUpdateUserPreferences(profileInformationDto);
    }
}
