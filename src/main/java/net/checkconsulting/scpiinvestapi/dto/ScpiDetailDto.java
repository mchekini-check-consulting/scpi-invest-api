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
    private Long capitalization;
    private String manager;
    private Float subscriptionFees;
    private String advertising;
    private Float managementFees;
    private Integer delayBenefit;
    private String rentFrequency;
    private Map<Integer, Float> distributionRate;
    private Map<Integer, Float> reconstitutionValue;
    private Map<Integer, Float> prices;
    private Map<String, Float> localizations;
    private Map<String, Float> sectors;
    private Float cashback;
}