package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ScpiComparatorOutDto {
    private Integer id;
    private String name;
    private Double monthlyRevenue;
    private Double subscriptionFees;
    private Double cashback;
    private Long capitalization;
    private String rentFrequency;
    private Integer entryDelay;
    private Integer minimumSubscription;

}

