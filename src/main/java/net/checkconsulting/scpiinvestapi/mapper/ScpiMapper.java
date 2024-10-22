package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
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

    @Mapping(target = "lastYearDistributionRate", expression = "java(getMaxDistributionRate(scpi.getDistributionRate()))")
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

    default String getMaxDistributionRate(List<DistributionRate> distributionRates) {
        Optional<DistributionRate> maxDistributionRate = distributionRates.stream()
                .max(Comparator.comparing(DistributionRate::getDistributionRate));
        return maxDistributionRate.map(item -> item.getDistributionRate().toString())
                .orElse("N/A");
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


}
