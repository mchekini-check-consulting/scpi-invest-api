package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScpiOutDto {
    private Integer id;
    private String name;
    private String sector;
    private String localization;
    private Integer minimumSubscription;
    private String lastYearDistributionRate;
}
