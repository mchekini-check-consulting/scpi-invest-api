package net.checkconsulting.scpiinvestapi.resources;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.service.ScpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/scpi")
@Tag(name = "SCPI INVEST + API", description = "API pour gérer les SCPI")
public class ScpiResource {
    private final ScpiService scpiService;

    public ScpiResource(ScpiService scpiService) {
        this.scpiService = scpiService;
    }

    @Operation(summary = "Récupérer toutes les SCPI", description = "Renvoie une liste de toutes les SCPI disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des SCPI renvoyée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<ScpiOutDto>> getAllScpi() {
        return ResponseEntity.ok().body(scpiService.findAllScpi());
    }

    @Operation(summary = "Récupérer une SCPI par ID", description = "Renvoie les détails d'une SCPI spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SCPI trouvée avec succès"),
            @ApiResponse(responseCode = "404", description = "SCPI non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScpiDetailDto> getScpiById(@PathVariable Integer id) throws Exception {
        ScpiDetailDto scpi = scpiService.findScpiById(id);
        return ResponseEntity.ok(scpi);
    }
}
