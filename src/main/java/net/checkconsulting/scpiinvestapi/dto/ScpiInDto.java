package net.checkconsulting.scpiinvestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScpiInDto {
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

    private List<LocalizationDto> localizations;
    private List<DistributionRateDto> distributionRates;
    private List<SectorDto> sectors;
    private List<PriceDto> prices;
    private List<DiscountStrippingDto> discountStrippings;

    // LOL Désolé pour ce désordre, mais je ne voulais pas encombrer le dossier avec toutes ces classes. J'ai préféré faire à l'ancienne, comme on dit.

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LocalizationDto {
        private String location;
        private Float percent;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DistributionRateDto {
        private Integer year;
        private Float distributionRate;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SectorDto {
        private String sectorName;
        private Float percent;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PriceDto {
        private Integer year;
        private Float price;
        private Float reconstitution;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiscountStrippingDto {
        private Integer year;
        private Float discount;
    }
}
