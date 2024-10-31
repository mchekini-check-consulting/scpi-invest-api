package net.checkconsulting.scpiinvestapi.service;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;


@Component
@Data
@AllArgsConstructor
@Builder
@Scope(scopeName = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

    private String email;
    private String username;
    private String lastName;
    private String firstName;
    private List<String> roles = new ArrayList<>();
    private String userId;


    public UserService() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = ((Jwt) authentication.getPrincipal()).getClaims().get("preferred_username").toString();
        email = ((Jwt) authentication.getPrincipal()).getClaims().get("email").toString();
        lastName = ((Jwt) authentication.getPrincipal()).getClaims().get("family_name").toString();
        firstName = ((Jwt) authentication.getPrincipal()).getClaims().get("given_name").toString();
        roles = authentication.getAuthorities().stream().map(Object::toString).toList();
        userId = ((Jwt) authentication.getPrincipal()).getClaims().get("sub").toString();


    }
}
