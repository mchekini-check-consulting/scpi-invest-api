package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioPerformanceDto {

    private Map<String, Integer> localizations;
    private Map<String, Integer> sectors;
    private Map<String, Map<Integer, Float>> partPrices;
}
