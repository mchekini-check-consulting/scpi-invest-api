package net.checkconsulting.scpiinvestapi.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.checkconsulting.scpiinvestapi.service.InvestmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/portfolio")
public class InvestmentResource {
    private final InvestmentService investmentService;

    public InvestmentResource(InvestmentService investmentService){
        this.investmentService = investmentService;
    }
    @Operation(summary = "Récupérer le portfolio des performances des investissement",
            description = "Récupère la performance du portefeuille par répartition géographique, répartition sectorielle et évolution des prix.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "le portfolio des performances des investissement récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/performance")
    public ResponseEntity<Map<String, Object>> getPortfolioPerformance() {
        Map<String, Object> performance = investmentService.getPortfolioPerformance();
        return ResponseEntity.ok(performance);
    }
}
