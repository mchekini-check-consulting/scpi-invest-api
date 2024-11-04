package net.checkconsulting.scpiinvestapi.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.BankInfo;
import net.checkconsulting.scpiinvestapi.dto.InvestmentDtoIn;
import net.checkconsulting.scpiinvestapi.dto.InvestmentForSimulationDto;
import net.checkconsulting.scpiinvestapi.dto.PortfolioPerformanceDto;
import net.checkconsulting.scpiinvestapi.service.InvestmentService;
import net.checkconsulting.scpiinvestapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/investement")
@Slf4j
public class InvestmentResource {
    private final InvestmentService investmentService;
    private final UserService userService;

    public InvestmentResource(InvestmentService investmentService, UserService userService){
        this.investmentService = investmentService;
        this.userService = userService;
    }

    @Operation(summary = "Récupérer Tous les investissments de l'utilisateur",
            description = "Récupère la liste de tous les investissments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "la liste des investissement est récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<?> getUserInvestments() {
        return ResponseEntity.status(HttpStatus.OK).body(this.investmentService.getUserInvestments());
    }

    @Operation(summary = "Récupérer le portfolio des performances des investissement",
            description = "Récupère la performance du portefeuille par répartition géographique, répartition sectorielle et évolution des prix.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "le portfolio des performances des investissement récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/portfolio/performance")
    public ResponseEntity<PortfolioPerformanceDto> getPortfolioPerformance() {
        return ResponseEntity.ok(investmentService.getPortfolioPerformance());
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Investment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "SCPI not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> createScpiInvestment(@RequestBody InvestmentDtoIn investment){
        try{
            BankInfo result = this.investmentService.createInvestment(investment);

            log.info("investment {} created successfully",result.getLabel());
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        }catch (NoSuchElementException e) {

            log.info("error: {}",e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {

            log.info("Invalid input: {}",e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {

            log.info("An unexpected error occurred:: {}",e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Récupérer les données de simulation d'investissement",
            description = "Retourne une liste de données d'investissement préparées pour la simulation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulations d'investissement récupérées avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvestmentForSimulationDto.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/investment-simulation")
    public ResponseEntity<List<InvestmentForSimulationDto>> getInvestmentForSimulation() {
        return ResponseEntity.status(HttpStatus.OK).body(this.investmentService.getInvestmentForSimulation());
    }

    @GetMapping("/total")
    public ResponseEntity<?> getTotalAndRentInvestments() {

        return ResponseEntity.ok(investmentService.getTotalAndRentInvestments(userService.getEmail()));
    }
}
