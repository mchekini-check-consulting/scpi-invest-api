package net.checkconsulting.scpiinvestapi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocalizationId implements java.io.Serializable {

    private Integer scpiId;
    private String country;
}
