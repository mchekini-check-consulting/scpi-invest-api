package net.checkconsulting.scpiinvestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Scpi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scpi_seq_gen")
    @SequenceGenerator(name = "scpi_seq_gen", sequenceName = "scpi_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer minimumSubscription;
    private Long capitalization;
    private String manager;
    private Float subscriptionFees;
    private Float managementFees;
    private Integer delayBenefit;
    private String rentFrequency;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Localization> localizations;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DistributionRate> distributionRate;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sector> sectors;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices;
}
