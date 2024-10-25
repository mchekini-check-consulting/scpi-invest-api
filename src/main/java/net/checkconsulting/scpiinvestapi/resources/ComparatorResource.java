package net.checkconsulting.scpiinvestapi.resources;

import lombok.Getter;
import lombok.Setter;
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
public class ComparatorResource {

    private final ComparatorService comparatorService;

    @Autowired
    public ComparatorResource(ComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @PostMapping("/compare")
    public List<ScpiComparatorOutDto> getInfoScpiForComparison(@RequestBody ComparisonRequest request) {
        return comparatorService.getInfoScpiForComparison(request.selectedScpis, request.getInvestValue());
    }

    @Setter
    @Getter
    public static class ComparisonRequest {
        private List<String> selectedScpis;
        private double investValue;

    }
}
