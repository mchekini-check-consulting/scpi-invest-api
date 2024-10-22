package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvestmentOutDto {

    private Integer scpiId;
    private String scpiName;
    private InvestStatus investmentStatus;
    private LocalDateTime requestDate;
    private LocalDateTime investmentValidationDate;
    private Float totalAmount;
    private Float totalReconstitutionValue;
    private Float distributionRate;
}
