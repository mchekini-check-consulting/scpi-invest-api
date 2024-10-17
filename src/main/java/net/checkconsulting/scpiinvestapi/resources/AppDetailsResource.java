package net.checkconsulting.scpiinvestapi.resources;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.AppDetailsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class AppDetailsResource {


    @Operation(summary = "Récupérer les détails de l'application",
            description = "Renvoie le nom de l'application ainsi que la version actuelle")
    @GetMapping("details")
    public AppDetailsDto getApplicationDetails(){

        log.info("Application name = SCPI-INVEST+ , version = 1.0.0");

        return AppDetailsDto.builder()
                .applicationName("SCPI-INVEST+")
                .applicationVersion("1.0.0")
                .build();
    }
}
