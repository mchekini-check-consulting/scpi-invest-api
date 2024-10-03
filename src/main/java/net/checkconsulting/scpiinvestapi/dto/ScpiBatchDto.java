package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScpiBatchDto {

    private String name;
    private Integer minimumSubscription;
    private Integer capitalization;
    private String manager;
    private Integer subscriptionFees;
    private Integer managementFees;
    private Integer delayBenefit;
    private String rentFrequency;
    private String distributionRate;
    private String reconstitutionValue;
    private String prices;
    private String localizations;
    private String sectors;
}
