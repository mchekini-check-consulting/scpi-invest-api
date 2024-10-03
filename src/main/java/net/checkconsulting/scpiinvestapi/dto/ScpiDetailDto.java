package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private List<Map<String, Object>> distributionRate;
    private String reconstitutionValue;
    private List<Map<String, Object>> prices;
    private List<Map<String, Object>> localizations;
    private List<Map<String, Object>> sectors;

}
