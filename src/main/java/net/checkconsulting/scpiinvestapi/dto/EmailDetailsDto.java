package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetailsDto {

    private String investorName;
    private String investmentAmount;
    private String scpiName;
    private String numberOfShares;
    private String sharePrice;
    private String propertyType;
    private String investmentDuration;
    private String iban;
    private String bic;
    private String companyName;
}
