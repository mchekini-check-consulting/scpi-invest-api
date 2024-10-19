package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentDtoIn {
    private Integer scpiId;
    private String propertyType;
    private Integer numberOfShares;
    private Integer stripping;
}
