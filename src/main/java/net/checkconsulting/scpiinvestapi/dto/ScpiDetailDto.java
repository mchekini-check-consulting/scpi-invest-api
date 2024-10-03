package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScpiDetailDto {
    private Integer id;
    private String name;
    private Integer minimumSubscription;
    private Integer capitalization;
    private String manager;
    private Integer subscriptionFees;
    private Integer managementFees;
    private Integer delayBenefit;
    private String rentFrequency;
    private Map<Integer, Float> distributionRate;
    private Map<Integer, Float> reconstitutionValue;
    private Map<Integer, Float> prices;
    private Map<String, Float> localizations;
    private Map<String, Float> sectors;

}