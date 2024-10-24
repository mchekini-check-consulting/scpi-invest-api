package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.checkconsulting.scpiinvestapi.enums.Frequency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanifiedInvestmentDto {

    private Frequency frequency;
    private Float amount;
    private Integer debitDayOfMonth;
    private Integer numberOfShares;
    private Integer scpi;
}
