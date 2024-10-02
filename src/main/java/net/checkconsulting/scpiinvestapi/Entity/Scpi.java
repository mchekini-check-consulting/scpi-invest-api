package net.checkconsulting.scpiinvestapi.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Scpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer minimumSubscription;
    private Integer capitalization;
    private String manager;
    private Integer subscriptionFees;
    private Integer managementFees;
    private Integer delayBenefit;
    private String rentFrequency;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Localization> localisations;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DistributionRate> tauxDistributions;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sector> sectors;

    @OneToMany(mappedBy = "scpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices;
}
