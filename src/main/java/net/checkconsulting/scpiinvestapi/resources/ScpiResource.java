package net.checkconsulting.scpiinvestapi.resources;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiMultiSearchInDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.service.ScpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/scpi")
public class ScpiResource {
    private final ScpiService scpiService;

    public ScpiResource(ScpiService scpiService) {
        this.scpiService = scpiService;
    }

    @Operation(summary = "Récupérer toutes les SCPI",
            description = "Renvoie toutes les SCPI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de SCPI récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<ScpiOutDto>> getAllScpi() {
        return ResponseEntity.ok().body(scpiService.findAllScpi());
    }

    @Operation(summary = "Récupérer les détails d'une SCPI par son ID",
            description = "Renvoie les détails d'une SCPI spécifique en fonction de l'ID fourni.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Détails de la SCPI récupérés avec succès"),
            @ApiResponse(responseCode = "404", description = "SCPI non trouvée pour l'ID spécifié"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScpiDetailDto> getScpiById(@PathVariable Integer id) throws Exception {
        ScpiDetailDto scpi = scpiService.findScpiById(id);
        return ResponseEntity.ok(scpi);
    }

    @Operation(summary = "Récupérer toutes les SCPI ou filtrer selon des critères",
            description = "Renvoie toutes les SCPI si aucun critère n'est fourni dans le body ({}). "
                    + "Permet aussi de filtrer les SCPI en fonction des critères passés dans le body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de SCPI récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/search")
    public ResponseEntity<List<ScpiOutDto>> getScpiByFilter(@RequestBody ScpiMultiSearchInDto scpiMultiSearchInDto) {
        return ResponseEntity.ok(scpiService.findScpiWithFilters(scpiMultiSearchInDto));
    }
}
