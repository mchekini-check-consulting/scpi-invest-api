package net.checkconsulting.scpiinvestapi.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Price {

    @EmbeddedId
    private PriceId id;

    private Float price;
    private Float reconstitution;

    @ManyToOne
    @MapsId("scpiId")
    @JoinColumn(name = "scpi_id")
    private Scpi scpi;
}

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
class PriceId implements java.io.Serializable {
    private Integer scpiId;
    private Integer year;
}
