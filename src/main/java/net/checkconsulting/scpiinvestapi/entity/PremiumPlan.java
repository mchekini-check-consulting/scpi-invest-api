package net.checkconsulting.scpiinvestapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumPlan {
    @Id
    private Integer id;
    private String functionality;
    private Boolean standard;
    private Boolean premium;
    private String description;
    private Integer codeReference;
}
