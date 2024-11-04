package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestmentSummaryDto {
    public double cashbackTotal;
    public double averageIncomePercent;
    public double totalInvest;
    public double reelValueInvested;
}
