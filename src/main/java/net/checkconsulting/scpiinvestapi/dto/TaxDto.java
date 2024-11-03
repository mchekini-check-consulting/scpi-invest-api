package net.checkconsulting.scpiinvestapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxDto {

    private Integer amount;
    private Float averageRate;
    private Integer tmi;

}
