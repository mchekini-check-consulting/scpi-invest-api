package net.checkconsulting.scpiinvestapi.resources;

import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ScpiComparatorOutDto;
import net.checkconsulting.scpiinvestapi.service.ComparatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/scpi")
@Slf4j
public class ComparatorResource {

    private final ComparatorService comparatorService;

    @Autowired
    public ComparatorResource(ComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @Operation(summary = "Compare des SCPIs",
            description = "Retourne les informations de comparaison pour une liste de SCPIs sélectionnées et une valeur d'investissement")

    @PostMapping("/compare")
    public List<ScpiComparatorOutDto> getInfoScpiForComparison(@RequestBody ComparisonRequest request) {
        log.info("Received comparison request with selected SCPIs: {} and invest value: {}", request.getSelectedScpis(), request.getInvestValue());
        return comparatorService.getInfoScpiForComparison(request.selectedScpis, request.getInvestValue());
    }

    @Setter
    @Getter
    public static class ComparisonRequest {
        private List<String> selectedScpis;
        private double investValue;

    }
}
