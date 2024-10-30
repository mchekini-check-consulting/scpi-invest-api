package net.checkconsulting.scpiinvestapi.batch;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ScpiWriter implements ItemWriter<Scpi> {

    private final ScpiRepository scpiRepository;

    public ScpiWriter(ScpiRepository scpiRepository) {
        this.scpiRepository = scpiRepository;
    }

    @Override
    public void write(Chunk<? extends Scpi> chunk) throws Exception {

        List<Scpi> newScpiToSave = new ArrayList<>();

        for (Scpi newScpi : chunk.getItems()){
            List<Scpi> existedScpi = scpiRepository.findByName(newScpi.getName());
            if(!existedScpi.isEmpty()) {
                newScpiToSave.add(updateScpi(existedScpi.get(0), newScpi));
            } else {
                newScpiToSave.add(newScpi);
            }
        }

        scpiRepository.saveAll(newScpiToSave);
    }

    private Scpi updateScpi(Scpi oldScpi, Scpi newScpi) {

        newScpi.getLocalizations()
                .forEach(localization -> {
                    localization.getId().setScpiId(oldScpi.getId());
                    localization.setScpi(oldScpi);
                });


        newScpi.getSectors()
                .forEach(sector -> {
                    sector.getId().setScpiId(oldScpi.getId());
                    sector.setScpi(oldScpi);
                });


        newScpi.getDistributionRate()
                .forEach(dr -> {
                    dr.getId().setScpiId(oldScpi.getId());
                    dr.setScpi(oldScpi);
                });

        newScpi.getPrices()
                .forEach(price -> {
                    price.getId().setScpiId(oldScpi.getId());
                    price.setScpi(oldScpi);
                });

        if (newScpi.getDiscountStripping()!= null ) {
            newScpi.getDiscountStripping().stream()
                    .forEach(ds -> {
                        ds.getId().setScpiId(oldScpi.getId());
                        ds.setScpi(oldScpi);
                    });
        }




        return Scpi.builder()
                .id(oldScpi.getId())
                .name(oldScpi.getName())
                .minimumSubscription(newScpi.getMinimumSubscription())
                .capitalization(newScpi.getCapitalization())
                .manager(newScpi.getManager())
                .subscriptionFees(newScpi.getSubscriptionFees())
                .managementFees(newScpi.getManagementFees())
                .delayBenefit(newScpi.getDelayBenefit())
                .rentFrequency(newScpi.getRentFrequency())
                .iban(newScpi.getIban())
                .bic(newScpi.getBic())
                .isStripping(newScpi.getIsStripping())
                .cashback(newScpi.getCashback())
                .isPlanedInvestment(newScpi.getIsPlanedInvestment())
                .advertising(newScpi.getAdvertising())
                .localizations(newScpi.getLocalizations())
                .distributionRate(newScpi.getDistributionRate())
                .sectors(newScpi.getSectors())
                .prices(newScpi.getPrices())
                .discountStripping(newScpi.getDiscountStripping())
                .build();

    }

}
