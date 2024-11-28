package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiInDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ScpiMapper {

    @Mapping(target = "lastYearDistributionRate", expression = "java(getLastDistributionRate(scpi.getDistributionRate()))")
    @Mapping(target = "sector", expression = "java(getMaxSectorPercent(scpi.getSectors()))")
    @Mapping(target = "localization", expression = "java(getMaxLocalizationPercent(scpi.getLocalizations()))")
    ScpiOutDto mapToScpiOutDto(Scpi scpi);


    @Mapping(target = "distributionRate", expression = "java(listToMapOfDistributionRate(scpi))")
    @Mapping(target = "reconstitutionValue", expression = "java(listToMapOfReconstitutionValue(scpi))")
    @Mapping(target = "prices", expression = "java(listToMapOfPrices(scpi))")
    @Mapping(target = "localizations", expression = "java(listToMapOfLocalizations(scpi))")
    @Mapping(target = "sectors", expression = "java(listToMapOfSectors(scpi))")
    ScpiDetailDto mapToScpiDetailDto(Scpi scpi);

    default Map<Integer, Float> listToMapOfDistributionRate(Scpi scpi) {
        return scpi.getDistributionRate().stream()
                .collect(Collectors.toMap(dr -> dr.getId().getYear(), DistributionRate::getDistributionRate));
    }

    default Map<Integer, Float> listToMapOfReconstitutionValue(Scpi scpi) {
        return scpi.getPrices().stream()
                .collect(Collectors.toMap(price -> price.getId().getYear(), Price::getReconstitution));
    }

    default Map<Integer, Float> listToMapOfPrices(Scpi scpi) {
        return scpi.getPrices().stream()
                .collect(Collectors.toMap(price -> price.getId().getYear(), Price::getPrice));
    }

    default Map<String, Float> listToMapOfLocalizations(Scpi scpi) {
        return scpi.getLocalizations().stream()
                .collect(Collectors.toMap(localization -> localization.getId().getCountry(), Localization::getPercent));
    }

    default Map<String, Float> listToMapOfSectors(Scpi scpi) {
        return scpi.getSectors().stream()
                .collect(Collectors.toMap(sector -> sector.getId().getSector(), Sector::getPercent));
    }

    default String getLastDistributionRate(List<DistributionRate> distributionRates) {
        return distributionRates.stream()
                .max(Comparator.comparing(dr -> dr.getId().getYear()))
                .map(dr -> dr.getDistributionRate().toString()).orElse("N/A");
    }

    default String getMaxSectorPercent(List<Sector> sectors) {
        Optional<Sector> maxSector = sectors.stream()
                .max(Comparator.comparing(Sector::getPercent));
        return maxSector.map(item -> item.getId().getSector())
                .orElse("N/A");
    }

    default String getMaxLocalizationPercent(List<Localization> localizations) {
        Optional<Localization> maxLocalization = localizations.stream()
                .max(Comparator.comparing(Localization::getPercent));
        return maxLocalization.map(item -> item.getId().getCountry())
                .orElse("N/A");
    }


    default  Scpi scpiWithoutRelation(ScpiInDto scpiInDto) {
        return Scpi.builder()
                .name(scpiInDto.getName())
                .minimumSubscription(scpiInDto.getMinimumSubscription())
                .capitalization(scpiInDto.getCapitalization())
                .manager(scpiInDto.getManager())
                .subscriptionFees(scpiInDto.getSubscriptionFees())
                .managementFees(scpiInDto.getManagementFees())
                .delayBenefit(scpiInDto.getDelayBenefit())
                .rentFrequency(scpiInDto.getRentFrequency())
                .iban(scpiInDto.getIban())
                .bic(scpiInDto.getBic())
                .isStripping(scpiInDto.getIsStripping())
                .cashback(scpiInDto.getCashback())
                .isPlanedInvestment(scpiInDto.getIsPlanedInvestment())
                .advertising(scpiInDto.getAdvertising())
                .build();
    }

    default   List<Localization> toLocalization(ScpiInDto scpiInDto,Scpi scpi) {
        return scpiInDto.getLocalizations().stream().map((item) -> Localization.builder()
                .id(LocalizationId.builder()
                        .country(item.getLocation())
                        .scpiId(scpi.getId())
                        .build())
                .scpi(scpi)
                .percent(item.getPercent())
                .build()).toList();
    }

    default    List<Sector> toSector(ScpiInDto scpiInDto,Scpi scpi) {
        return scpiInDto.getSectors().stream().map((item) -> Sector.builder()
                .id(SectorId.builder()
                        .scpiId(scpi.getId())
                        .sector(item.getSectorName())
                        .build())
                .scpi(scpi)
                .percent(item.getPercent())
                .build()).toList();
    }

    default  List<DistributionRate> toDistributionRate(ScpiInDto scpiInDto,Scpi scpi) {
        return scpiInDto.getDistributionRates().stream().map((item) -> DistributionRate.builder()
                .id(DistributionRateId.builder()
                        .scpiId(scpi.getId())
                        .year(item.getYear())
                        .build())
                .scpi(scpi)
                .distributionRate(item.getDistributionRate())
                .build()).toList();
    }

    default  List<Price> toPrice(ScpiInDto scpiInDto,Scpi scpi) {
        return scpiInDto.getPrices().stream().map((item) -> Price.builder()
                .id(PriceId.builder()
                        .scpiId(scpi.getId())
                        .year(item.getYear())
                        .build())
                .scpi(scpi)
                .price(item.getPrice())
                .reconstitution(item.getReconstitution())
                .build()).toList();
    }

    default List<DiscountStripping> toDiscountStripping(ScpiInDto scpiInDto,Scpi scpi) {
        return scpiInDto.getDiscountStrippings().stream().map((item) ->
                DiscountStripping.builder()
                        .id(DiscountStrippingId.builder()
                                .scpiId(scpi.getId())
                                .year(item.getYear())
                                .build())
                        .scpi(scpi)
                        .discount(item.getDiscount())
                        .build()).toList();
    }



}
