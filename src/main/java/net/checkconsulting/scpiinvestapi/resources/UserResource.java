package net.checkconsulting.scpiinvestapi.resources;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ProfileInformationDto;
import net.checkconsulting.scpiinvestapi.service.KeycloakAdminService;
import net.checkconsulting.scpiinvestapi.service.UserPrefenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserResource {

    private final KeycloakAdminService keycloakService;
    private final UserPrefenceService userPrefenceService;

    public UserResource(KeycloakAdminService keycloakService, UserPrefenceService userPrefenceService) {
        this.keycloakService = keycloakService;
        this.userPrefenceService = userPrefenceService;
    }


    @PutMapping
    public void updateRole(@RequestParam("newRole") String newRole) {
        log.info("Update role with new role : {}", newRole);
        keycloakService.updateRoleForUser(newRole);
    }

    @GetMapping("preference")
    public ProfileInformationDto getUserPreference(){
        log.info("Get user preference");
        return userPrefenceService.getUserPreference();
    }

    @PostMapping("/preference")
    public void createOrUpdateUserPreferences(@RequestBody ProfileInformationDto profileInformationDto){
        log.info("Update or create user preferences with profile : {}", profileInformationDto);
        userPrefenceService.createOrUpdateUserPreferences(profileInformationDto);
    }
}
