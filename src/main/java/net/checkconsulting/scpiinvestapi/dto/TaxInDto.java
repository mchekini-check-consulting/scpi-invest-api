package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.checkconsulting.scpiinvestapi.enums.FamilySituation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxInDto {

    private Long annualIncome;
    private FamilySituation familySituation;
    private Integer numberOfChildren;
}
