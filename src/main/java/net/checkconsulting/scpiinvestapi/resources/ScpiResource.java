package net.checkconsulting.scpiinvestapi.resources;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiInDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiMultiSearchInDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.service.ScpiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @Operation(
            summary = "Créer une nouvelle SCPI",
            description = "Permet de créer une nouvelle SCPI. Retourne un code 201 avec l'URL d'accès à la ressource dans l'en-tête 'Location'.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données nécessaires pour créer une SCPI",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ScpiInDto.class)
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "SCPI créée avec succès. L'URL d'accès à la ressource est retournée.",
                            headers = @Header(
                                    name = "Location",
                                    description = "URL de la ressource créée",
                                    schema = @Schema(type = "string", format = "uri")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requête invalide. Vérifiez les données fournies.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erreur interne du serveur.",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Map<String,URI>> createScpi(@RequestBody ScpiInDto scpi) {
        Integer scpiId = scpiService.createScpi(scpi);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(scpiId)
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("location", location));
    }

}
