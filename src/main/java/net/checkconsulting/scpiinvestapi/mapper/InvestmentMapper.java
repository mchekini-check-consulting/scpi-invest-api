package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.InvestmentOutDto;
import net.checkconsulting.scpiinvestapi.entity.Investment;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;

@Mapper(componentModel = "spring")
public interface InvestmentMapper {

    @Mapping(target = "scpiId", expression = "java(investment.getScpi().getId())")
    @Mapping(target = "scpiName", expression = "java(investment.getScpi().getName())")
    @Mapping(target = "distributionRate", expression = "java(getLastYearDistributionRate(investment.getScpi()))")
    @Mapping(target = "currentValue", expression = "java(getCurrentValue(investment.getScpi(), investment.getNumberOfShares()))")
    @Mapping(target = "investmentValidationDate", source = "statusChangeDate")
    InvestmentOutDto mapToInvestmentOutDto(Investment investment);

    default Float getLastYearDistributionRate(Scpi scpi) {
         return  scpi.getDistributionRate().get(0).getDistributionRate();
    }

    default Float getCurrentValue(Scpi scpi, Integer numberOfParts) {
        return scpi.getPrices().stream().max(Comparator.comparingInt(p -> p.getId().getYear())).get().getPrice() * numberOfParts;
    }
}
