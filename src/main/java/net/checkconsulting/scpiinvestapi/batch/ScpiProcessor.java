package net.checkconsulting.scpiinvestapi.batch;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ScpiDto;
import net.checkconsulting.scpiinvestapi.entity.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ScpiProcessor implements ItemProcessor<ScpiDto, Scpi> {

    @Override
    public Scpi process(ScpiDto scpiDto) throws Exception {

        Scpi scpi = Scpi.builder()
                .name(scpiDto.getName())
                .capitalization(scpiDto.getCapitalization())
                .delayBenefit(scpiDto.getDelayBenefit())
                .managementFees(scpiDto.getManagementFees())
                .rentFrequency(scpiDto.getRentFrequency())
                .manager(scpiDto.getManager())
                .minimumSubscription(scpiDto.getMinimumSubscription())
                .subscriptionFees(scpiDto.getSubscriptionFees())
                .build();

        setDistributionRate(scpiDto, scpi);
        setLocalizations(scpiDto, scpi);
        setSectors(scpiDto, scpi);
        setPricesAndReconstitutionsValue(scpiDto, scpi);

        return scpi;
    }

    private void setPricesAndReconstitutionsValue(ScpiDto scpiDto, Scpi scpi) {
        List<Price> prices = new ArrayList<>();

        int date = LocalDate.now().getYear();

        for (int i = 0; i < scpiDto.getPrices().split(",").length; i++) {

            prices.add(Price.builder()
                    .id(PriceId.builder()
                            .scpiId(scpi.getId())
                            .year(date)
                            .build())
                    .scpi(scpi)
                    .price(Float.valueOf(scpiDto.getPrices().split(",")[i]))
                    .reconstitution(Float.valueOf(scpiDto.getReconstitutionValue().split(",")[i]))
                    .build());

            date -= 1;
        }

        scpi.setPrices(prices);
    }

    private void setSectors(ScpiDto scpiDto, Scpi scpi) {
        List<Sector> sectors = new ArrayList<>();

        for (int i = 0; i < scpiDto.getSectors().split(",").length; i += 2) {

            String sector = scpiDto.getSectors().split(",")[i];
            Float percent = Float.valueOf(scpiDto.getSectors().split(",")[i + 1]);

            sectors.add(Sector.builder()
                    .id(SectorId.builder()
                            .scpiId(scpi.getId())
                            .sector(sector)
                            .build())
                    .percent(percent)
                    .scpi(scpi)
                    .build());
        }
        scpi.setSectors(sectors);
    }

    private void setLocalizations(ScpiDto scpiDto, Scpi scpi) {
        List<Localization> localizations = new ArrayList<>();

        for (int i = 0; i < scpiDto.getLocalizations().split(",").length; i += 2) {

            String country = scpiDto.getLocalizations().split(",")[i];
            Float percent = Float.valueOf(scpiDto.getLocalizations().split(",")[i + 1]);

            localizations.add(Localization.builder()
                    .id(LocalizationId.builder()
                            .scpiId(scpi.getId())
                            .country(country)
                            .build())
                    .percent(percent)
                    .scpi(scpi)
                    .build());
        }
        scpi.setLocalizations(localizations);
    }

    private void setDistributionRate(ScpiDto scpiDto, Scpi scpi) {
        int date = LocalDate.now().getYear() - 1;

        List<DistributionRate> distributionRate = new ArrayList<>();

        for (String s : scpiDto.getDistributionRate().split(",")) {

            distributionRate.add(DistributionRate.builder()
                    .id(DistributionRateId.builder()
                            .scpiId(scpi.getId())
                            .year(date)
                            .build())
                    .distributionRate(Float.valueOf(s))
                    .scpi(scpi)
                    .build());

            date -= 1;
        }

        scpi.setDistributionRate(distributionRate);
    }
}