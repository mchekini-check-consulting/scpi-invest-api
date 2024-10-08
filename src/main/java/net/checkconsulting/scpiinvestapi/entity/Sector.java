package net.checkconsulting.scpiinvestapi.entity;

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
public class Sector {

    @EmbeddedId
    private SectorId id;

    private Float percent;

    @ManyToOne
    @MapsId("scpiId")
    @JoinColumn(name = "scpi_id")
    private Scpi scpi;
}

