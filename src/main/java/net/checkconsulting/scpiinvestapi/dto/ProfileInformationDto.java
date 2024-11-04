package net.checkconsulting.scpiinvestapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.checkconsulting.scpiinvestapi.enums.FamilySituation;
import net.checkconsulting.scpiinvestapi.enums.ProfileType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileInformationDto {

    private LocalDate birthDate;
    private Long income;
    private FamilySituation familyStatus;
    private Integer childrenCount;
    private ProfileType profileType;
    private String profession;
    private String email;
}
