package net.checkconsulting.scpiinvestapi.batch;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ScpiBatchDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import static net.checkconsulting.scpiinvestapi.utils.Constants.SCPI_FILE_PATH;
@Slf4j
@Configuration
public class ScpiReader {

    @Bean
    public FlatFileItemReader reader() {

        log.info("Read csv");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource(SCPI_FILE_PATH);

        return new FlatFileItemReaderBuilder<>()
                .name("ScpiReader")
                .resource(resource)
                .linesToSkip(1)
                .delimited()
                .delimiter(";")
                .names(new String[]{"Nom", "taux_distribution", "minimum_souscription", "localisation",
                "secteurs", "prix_part", "capitalisation", "Gérant", "frais_souscription", "frais_gestion",
                "delai_jouissance", "fréquence_loyers", "valeur_reconstitution"})
                .fieldSetMapper(fieldSet -> {
                    ScpiBatchDto scpi = new ScpiBatchDto();

                    scpi.setName(fieldSet.readString(0));
                    scpi.setDistributionRate(fieldSet.readString(1));
                    scpi.setMinimumSubscription(Integer.valueOf(fieldSet.readString(2)));
                    scpi.setLocalizations(fieldSet.readString(3));
                    scpi.setSectors(fieldSet.readString(4));
                    scpi.setPrices(fieldSet.readString(5));
                    scpi.setCapitalization(Integer.valueOf(fieldSet.readString(6)));
                    scpi.setManager(fieldSet.readString(7));
                    scpi.setSubscriptionFees(Integer.valueOf(fieldSet.readString(8)));
                    scpi.setManagementFees(Integer.valueOf(fieldSet.readString(9)));
                    scpi.setDelayBenefit(Integer.valueOf(fieldSet.readString(10)));
                    scpi.setRentFrequency(fieldSet.readString(11));
                    scpi.setReconstitutionValue(fieldSet.readString(12));

                    return scpi;
                })
                .build();
    }
}