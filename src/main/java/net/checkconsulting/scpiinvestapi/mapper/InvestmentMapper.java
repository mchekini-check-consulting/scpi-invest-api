package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.InvestmentOutDto;
import net.checkconsulting.scpiinvestapi.entity.Investment;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvestmentMapper {

    @Mapping(target = "scpiId", expression = "java(investment.getScpi().getId())")
    @Mapping(target = "scpiName", expression = "java(investment.getScpi().getName())")
    @Mapping(target = "distributionRate", expression = "java(getLastYearDistributionRate(investment.getScpi()))")
    @Mapping(target = "totalReconstitutionValue", expression = "java(getReconstitutionValueOfAllParts(investment.getScpi(), investment.getNumberOfShares()))")
    @Mapping(target = "investmentValidationDate", source = "statusChangeDate")
    InvestmentOutDto mapToInvestmentOutDto(Investment investment);

    default Float getLastYearDistributionRate(Scpi scpi) {
         return  scpi.getDistributionRate().get(0).getDistributionRate();
    }

    default Float getReconstitutionValueOfAllParts(Scpi scpi, Integer numberOfParts) {
        return scpi.getPrices().get(0).getReconstitution() * numberOfParts;
    }
}
