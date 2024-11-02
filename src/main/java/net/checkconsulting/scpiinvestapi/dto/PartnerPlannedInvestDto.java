package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerPlannedInvestDto {
    private String firstName;
    private String lastName;
    private String email;
    private double amount;
    private String frequency;
    private int debitDayOfMonth;
    private int numberOfShares;
    private String propertyType;
    private String label;

}
