package net.checkconsulting.scpiinvestapi.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvestmentInfoDto {
    private String investmentLabel;
    private String partnerName;
    private String propertyType;
    @Nullable
    private Integer numberOfShares;
    private Float totalAmount;
    private Integer stripping;

}
