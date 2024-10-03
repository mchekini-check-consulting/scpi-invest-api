package net.checkconsulting.scpiinvestapi.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
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
public class ScpiDto {

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
