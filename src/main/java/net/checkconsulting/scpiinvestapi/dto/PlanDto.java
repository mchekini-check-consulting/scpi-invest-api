package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {
    private Integer id;
    private String functionality;
    private Boolean standard;
    private Boolean premium;
    private String description;
}
