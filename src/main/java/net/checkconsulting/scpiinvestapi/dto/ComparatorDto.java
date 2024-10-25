package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComparatorDto {
    private Long id;
    private String name;
    private Double monthlyRevenue;
    private Double subscriptionFees;
    private Double cashback;
    private Integer capitalization;
    private String rentFrequency;
    private Integer entryDelay;
    private Integer minInvestment;
}
