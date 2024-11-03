package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.checkconsulting.scpiinvestapi.enums.PropertyType;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvestmentForSimulationDto {
    private Integer scpiId;
    private String name;
    private PropertyType selectedProperty;
    private Integer totalInvest;
    private Integer partNb;
    private Float monthlyIncomes;
    private Float withdrawalValue;
    private Map<Integer, Float> strip;
    private String lastYearDistributionRate;
    private Map<String, Float> localizations;
    private Map<String, Float> sectors;
    private Float cashback;
}
