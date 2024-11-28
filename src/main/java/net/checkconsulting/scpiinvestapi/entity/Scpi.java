package net.checkconsulting.scpiinvestapi.entity;

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
    private String iban;
    private String bic;
    private Boolean isStripping;
    private Float cashback;
    private Boolean isPlanedInvestment;
    private String advertising;

    @OneToMany(mappedBy = "scpi", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Localization> localizations;

    @OneToMany(mappedBy = "scpi", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<DistributionRate> distributionRate;

    @OneToMany(mappedBy = "scpi", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Sector> sectors;

    @OneToMany(mappedBy = "scpi", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Price> prices;

    @OneToMany(mappedBy = "scpi", cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private List<DiscountStripping> discountStripping;

}
