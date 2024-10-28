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
    private Long capitalization;
    private String manager;
    private Float subscriptionFees;
    private Float managementFees;
    private Integer delayBenefit;
    private String rentFrequency;
    private String distributionRate;
    private String reconstitutionValue;
    private String prices;
    private String localizations;
    private String sectors;
    private String iban;
    private String bic;
    private String discountStripping;
    private Boolean isStripping;
    private Float cashback;
    private Boolean isPlanedInvestment;
    private String advertising;

}
