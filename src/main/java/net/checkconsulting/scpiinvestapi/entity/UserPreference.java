package net.checkconsulting.scpiinvestapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.checkconsulting.scpiinvestapi.enums.FamilySituation;
import net.checkconsulting.scpiinvestapi.enums.ProfileType;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {
    @Id
    private String email;
    private LocalDate birthDate;
    private Long income;
    @Enumerated(STRING)
    private FamilySituation familyStatus;
    private Integer childrenCount;
    @Enumerated(STRING)
    private ProfileType profileType;
    private String profession;

}
