package net.checkconsulting.scpiinvestapi.batch;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ScpiBatchDto;
import net.checkconsulting.scpiinvestapi.entity.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ScpiProcessor implements ItemProcessor<ScpiBatchDto, Scpi> {

    @Override
    public Scpi process(ScpiBatchDto scpiBatchDto) throws Exception {

        Scpi scpi = Scpi.builder()
                .name(scpiBatchDto.getName())
                .capitalization(scpiBatchDto.getCapitalization())
                .delayBenefit(scpiBatchDto.getDelayBenefit())
                .managementFees(scpiBatchDto.getManagementFees())
                .rentFrequency(scpiBatchDto.getRentFrequency())
                .manager(scpiBatchDto.getManager())
                .minimumSubscription(scpiBatchDto.getMinimumSubscription())
                .subscriptionFees(scpiBatchDto.getSubscriptionFees())
                .iban(scpiBatchDto.getIban())
                .bic(scpiBatchDto.getBic())
                .isStripping(scpiBatchDto.getIsStripping())
                .cashback(scpiBatchDto.getCashback())
                .isPlanedInvestment(scpiBatchDto.getIsPlanedInvestment())
                .advertising(scpiBatchDto.getAdvertising())
                .build();

        setDistributionRate(scpiBatchDto, scpi);
        setLocalizations(scpiBatchDto, scpi);
        setSectors(scpiBatchDto, scpi);
        setPricesAndReconstitutionsValue(scpiBatchDto, scpi);
        setDiscountStripping(scpiBatchDto, scpi);

        return scpi;
    }

    private void setDiscountStripping(ScpiBatchDto scpiBatchDto, Scpi scpi) {
        List<DiscountStripping> discountStrippings = new ArrayList<>();

        if(!scpiBatchDto.getDiscountStripping().isEmpty()) {

            for (int i = 0; i < scpiBatchDto.getDiscountStripping().split(",").length; i += 2) {
                discountStrippings.add(DiscountStripping.builder()
                        .id(DiscountStrippingId.builder()
                                .scpiId(scpi.getId())
                                .year(Integer.valueOf(scpiBatchDto.getDiscountStripping().split(",")[i]))
                                .build())
                        .scpi(scpi)
                        .discount(Float.valueOf(scpiBatchDto.getDiscountStripping().split(",")[i + 1]))
                        .build());
            }

            scpi.setDiscountStripping(discountStrippings);

        }
    }

    private void setPricesAndReconstitutionsValue(ScpiBatchDto scpiBatchDto, Scpi scpi) {
        List<Price> prices = new ArrayList<>();

        int date = LocalDate.now().getYear();

        for (int i = 0; i < scpiBatchDto.getPrices().split(",").length; i++) {

            prices.add(Price.builder()
                    .id(PriceId.builder()
                            .scpiId(scpi.getId())
                            .year(date)
                            .build())
                    .scpi(scpi)
                    .price(Float.valueOf(scpiBatchDto.getPrices().split(",")[i]))
                    .reconstitution(Float.valueOf(scpiBatchDto.getReconstitutionValue().split(",")[i]))
                    .build());

            date -= 1;
        }

        scpi.setPrices(prices);
    }

    private void setSectors(ScpiBatchDto scpiBatchDto, Scpi scpi) {
        List<Sector> sectors = new ArrayList<>();

        for (int i = 0; i < scpiBatchDto.getSectors().split(",").length; i += 2) {

            String sector = scpiBatchDto.getSectors().split(",")[i];
            Float percent = Float.valueOf(scpiBatchDto.getSectors().split(",")[i + 1]);

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

    private void setLocalizations(ScpiBatchDto scpiBatchDto, Scpi scpi) {
        List<Localization> localizations = new ArrayList<>();

        for (int i = 0; i < scpiBatchDto.getLocalizations().split(",").length; i += 2) {

            String country = scpiBatchDto.getLocalizations().split(",")[i];
            Float percent = Float.valueOf(scpiBatchDto.getLocalizations().split(",")[i + 1]);

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

    private void setDistributionRate(ScpiBatchDto scpiBatchDto, Scpi scpi) {
        int date = LocalDate.now().getYear() - 1;

        List<DistributionRate> distributionRate = new ArrayList<>();

        for (String s : scpiBatchDto.getDistributionRate().split(",")) {

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