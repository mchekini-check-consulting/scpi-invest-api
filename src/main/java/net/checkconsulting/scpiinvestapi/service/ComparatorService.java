package net.checkconsulting.scpiinvestapi.service;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ScpiComparatorOutDto;
import net.checkconsulting.scpiinvestapi.repository.ComparisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ComparatorService {

    private final ComparisonRepository comparisonRepository;

    @Autowired
    public ComparatorService(ComparisonRepository comparisonRepository) {
        this.comparisonRepository = comparisonRepository;
    }

    public List<ScpiComparatorOutDto> getInfoScpiForComparison(List<String> selectedScpis, double investValue) {
        log.info("Get Scpis: {} and investment value: {} for comparison ", selectedScpis, investValue);
        return comparisonRepository.getInfoScpiForComparison(selectedScpis, investValue);
    }
}
