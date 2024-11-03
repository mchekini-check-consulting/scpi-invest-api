package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.InvestmentForSimulationDto;
import net.checkconsulting.scpiinvestapi.dto.InvestmentOutDto;
import net.checkconsulting.scpiinvestapi.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InvestmentMapper {

    @Mapping(target = "scpiId", expression = "java(investment.getScpi().getId())")
    @Mapping(target = "scpiName", expression = "java(investment.getScpi().getName())")
    @Mapping(target = "distributionRate", expression = "java(getLastYearDistributionRate(investment.getScpi()))")
    @Mapping(target = "currentValue", expression = "java(getCurrentValue(investment.getScpi(), investment.getNumberOfShares()))")
    @Mapping(target = "detentionDays", expression = "java(calculateNumberOfDetentionDate(investment.getStatusChangeDate()))")
    InvestmentOutDto mapToInvestmentOutDto(Investment investment);

    default Float getLastYearDistributionRate(Scpi scpi) {
         return  scpi.getDistributionRate().get(0).getDistributionRate();
    }

    default Float getCurrentValue(Scpi scpi, Integer numberOfParts) {
        return scpi.getPrices().stream().max(Comparator.comparingInt(p -> p.getId().getYear())).get().getPrice() * numberOfParts;
    }

    default Long calculateNumberOfDetentionDate(LocalDateTime date){

        if (date == null) return 0L;

        return Duration.between(date, LocalDateTime.now()).toDays();
    }

    @Mapping(target = "scpiId", expression = "java(investment.getScpi().getId())")
    @Mapping(target = "name", expression = "java(investment.getScpi().getName())")
    @Mapping(target = "cashback", expression = "java(investment.getScpi().getCashback())")
    @Mapping(target = "selectedProperty", source = "propertyType")
    @Mapping(target = "totalInvest", source = "totalAmount")
    @Mapping(target = "strip", expression = "java(getListToMapOfDiscountStripping(investment))")
    @Mapping(target = "partNb", source = "numberOfShares")
    @Mapping(target = "sectors", expression = "java(getListToMapOfSectors(investment))")
    @Mapping(target = "localizations", expression = "java(getListToMapOfLocalisations(investment))")
    @Mapping(target = "lastYearDistributionRate", expression = "java(getLastYearDistributionRate(investment.getScpi()).toString())")
    @Mapping(target = "monthlyIncomes", expression = "java(getMonthlyIncomes(investment))")
    @Mapping(target = "withdrawalValue", expression = "java(getCurrentValue(investment.getScpi(), investment.getNumberOfShares()))")
    InvestmentForSimulationDto mapToInvestmentForSimulationDto(Investment investment);


    default Map<Integer, Float> getListToMapOfDiscountStripping(Investment investment) {
        return investment.getScpi().getDiscountStripping().stream().filter(ds -> ds.getId().getYear() == investment.getStripping())
                .collect(Collectors.toMap(ds -> ds.getId().getYear(), DiscountStripping::getDiscount));
    }
    default Map<String, Float> getListToMapOfSectors(Investment investment) {
        return investment.getScpi().getSectors().stream()
                .collect(Collectors.toMap(sector -> sector.getId().getSector(), Sector::getPercent));
    }

    default Map<String, Float> getListToMapOfLocalisations(Investment investment) {
        return investment.getScpi().getLocalizations().stream()
                .collect(Collectors.toMap(localisation -> localisation.getId().getCountry(), Localization::getPercent));
    }

    default Float getMonthlyIncomes(Investment investment) {
        return investment.getTotalAmount() * getLastYearDistributionRate(investment.getScpi()) / 100 / 12;
    }
}
