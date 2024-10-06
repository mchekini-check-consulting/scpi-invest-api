package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.entity.*;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScpiService {
    private final ScpiRepository scpiRepository;

    @Autowired
    public ScpiService(ScpiRepository scpiRepository){
        this.scpiRepository=scpiRepository;
    }

    public List<ScpiOutDto> findAllScpi() {
        return fromScpiToScpiDtoMapper(scpiRepository.findAll());
    }

    public ScpiDetailDto findScpiById(Integer id) throws Exception {
        Optional<Scpi> optionalScpi = scpiRepository.findById(id);
        if (optionalScpi.isEmpty()) throw new Exception("No SCPI with ID = " + id);

        return fromScpiToScpiDetailDtoMapper(optionalScpi.get());
    }

    public List<ScpiOutDto> fromScpiToScpiDtoMapper(List<Scpi> scpi){
        return scpi.stream().map(item->ScpiOutDto.builder()
                .name(item.getName())
                .sector(getMaxSectorPercent(item.getSectors()))
                .localization(getMaxLocalizationPorcent(item.getLocalizations()))
                .minimumSubscription(item.getMinimumSubscription())
                .lastYearDistributionRate(getMaxDistributionRate(item.getDistributionRate()))
                .id(item.getId())
                .build()).toList();
    }

    private ScpiDetailDto fromScpiToScpiDetailDtoMapper(Scpi scpi) {

        return ScpiDetailDto.builder()
                .id(scpi.getId())
                .name(scpi.getName())
                .minimumSubscription(scpi.getMinimumSubscription())
                .capitalization(scpi.getCapitalization())
                .manager(scpi.getManager())
                .subscriptionFees(scpi.getSubscriptionFees())
                .managementFees(scpi.getManagementFees())
                .delayBenefit(scpi.getDelayBenefit())
                .rentFrequency(scpi.getRentFrequency())
                .distributionRate(scpi.getDistributionRate().stream()
                        .collect(Collectors.toMap(dr -> dr.getId().getYear(), DistributionRate::getDistributionRate)))
                .prices(scpi.getPrices().stream()
                        .collect(Collectors.toMap(price -> price.getId().getYear(), Price::getPrice)))
                .reconstitutionValue(scpi.getPrices().stream()
                        .collect(Collectors.toMap(price -> price.getId().getYear(), Price::getReconstitution)))
                .sectors(scpi.getSectors().stream()
                        .collect(Collectors.toMap(sector -> sector.getId().getSector(), Sector::getPercent)))
                .localizations(scpi.getLocalizations().stream()
                        .collect(Collectors.toMap(localization -> localization.getId().getCountry(), Localization::getPercent)))
                .build();

    }


    private String getMaxDistributionRate(List<DistributionRate> distributionRates){
        Optional<DistributionRate> maxDistributionRate = distributionRates.stream()
                .max(Comparator.comparing(DistributionRate::getDistributionRate));
        return maxDistributionRate.map(item->item.getDistributionRate().toString())
                .orElse("N/A");
    }

    private String getMaxSectorPercent(List<Sector> sectors){
        Optional<Sector> maxSector = sectors.stream()
                .max(Comparator.comparing(Sector::getPercent));
        return maxSector.map(item->item.getId().getSector())
                .orElse("N/A");
    }

    private String getMaxLocalizationPorcent (List<Localization> localizations){
        Optional<Localization> maxLocalization = localizations.stream()
                .max(Comparator.comparing(Localization:: getPercent));
        return maxLocalization.map(item->item.getId().getCountry())
                .orElse("N/A");
    }

}
