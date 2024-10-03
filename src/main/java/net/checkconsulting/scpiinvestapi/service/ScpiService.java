package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.batch.ScpiWriter;
import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.entity.DistributionRate;
import net.checkconsulting.scpiinvestapi.entity.Localization;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.entity.Sector;
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

    public Optional<ScpiDetailDto> findScpiById(Integer id) {
        return fromScpiToScpiDetailDtoMapper(scpiRepository.findById(id));
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

    private Optional<ScpiDetailDto> fromScpiToScpiDetailDtoMapper(Optional<Scpi> scpiDetail) {
        return scpiDetail.map(scpi -> ScpiDetailDto.builder()
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
                        .map(rate -> {
                            Map<String, Object> rateMap = new HashMap<>();
                            rateMap.put("year", rate.getId().getYear());
                            rateMap.put("rate", rate.getDistributionRate());
                            return rateMap;
                        })
                        .collect(Collectors.toList()))
                .prices(scpi.getPrices().stream()
                        .map(price -> {
                            Map<String, Object> priceMap = new HashMap<>();
                            priceMap.put("date", price.getId().getYear());
                            priceMap.put("price", price.getPrice());
                            priceMap.put("reconstitution", price.getReconstitution());
                            return priceMap;
                        })
                        .collect(Collectors.toList()))
                .localizations(scpi.getLocalizations().stream()
                        .map(localization -> {
                            Map<String, Object> localizationMap = new HashMap<>();
                            localizationMap.put("country", localization.getId().getCountry());
                            localizationMap.put("percent", localization.getPercent());
                            return localizationMap;
                        })
                        .collect(Collectors.toList()))
                .sectors(scpi.getSectors().stream()
                        .map(sector -> {
                            Map<String, Object> sectorMap = new HashMap<>();
                            sectorMap.put("sector", sector.getId().getSector());
                            sectorMap.put("percent", sector.getPercent());
                            return sectorMap;
                        })
                        .collect(Collectors.toList()))
                .build());
    }


    private String getMaxDistributionRate(List<DistributionRate> distributionRates){
        Optional<DistributionRate> maxDistributionRate = distributionRates.stream()
                .max(Comparator.comparing(DistributionRate::getDistributionRate));
        return maxDistributionRate.map(item->item.getDistributionRate().toString())
                .orElse("Aucune Distribution trouvé");
    }

    private String getMaxSectorPercent(List<Sector> sectors){
        Optional<Sector> maxSector = sectors.stream()
                .max(Comparator.comparing(Sector::getPercent));
        return maxSector.map(item->item.getId().getSector())
                .orElse("Aucun secteur trouvé");
    }

    private String getMaxLocalizationPorcent (List<Localization> localizations){
        Optional<Localization> maxLocalization = localizations.stream()
                .max(Comparator.comparing(Localization:: getPercent));
        return maxLocalization.map(item->item.getId().getCountry())
                .orElse("Aucun Pays Trouvé");
    }

}
