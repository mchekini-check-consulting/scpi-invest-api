package net.checkconsulting.scpiinvestapi.cron;

import net.checkconsulting.scpiinvestapi.configuration.security.KeycloakConfiguration;
import net.checkconsulting.scpiinvestapi.dto.EmailDetailsDto;
import net.checkconsulting.scpiinvestapi.entity.Investment;
import net.checkconsulting.scpiinvestapi.entity.Price;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import net.checkconsulting.scpiinvestapi.feignClients.NotificationClient;
import net.checkconsulting.scpiinvestapi.repository.InvestmentRepository;
import net.checkconsulting.scpiinvestapi.service.KeycloakAdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static net.checkconsulting.scpiinvestapi.enums.EmailType.REMINDER_AFTER_TWO_DAYS;

@Component
public class SendEmail {

    @Value("${application.name}")
    String applicationName;
    private final InvestmentRepository investmentRepository;

    private final NotificationClient notificationClient;

    private final KeycloakAdminService keycloakService;

    private final KeycloakConfiguration keycloakConfiguration;

    public SendEmail(InvestmentRepository investmentRepository, NotificationClient notificationClient, KeycloakAdminService keycloakService, KeycloakConfiguration keycloakConfiguration) {
        this.investmentRepository = investmentRepository;
        this.notificationClient = notificationClient;
        this.keycloakService = keycloakService;
        this.keycloakConfiguration = keycloakConfiguration;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendEmailReminderAfterTwoDays() {
        LocalDateTime threeDayAgo = LocalDateTime.now().minusDays(3);
        LocalDateTime twoDayAgo = LocalDateTime.now().minusDays(2);
        List<Investment> investmentList =  investmentRepository.findInvestmentByRequestDateIsBetweenAndInvestmentStatusEquals(threeDayAgo, twoDayAgo, InvestStatus.PENDING);

        if(!investmentList.isEmpty()) {

            investmentList.stream().forEach(investment -> {
                float partPrice = extractLastPriceOfScpi(investment.getScpi().getPrices());
                String userName = keycloakService.getFirstNamByEmail(keycloakConfiguration.getRealm(),investment.getUserEmail()) + " " +
                         keycloakService.getLastNamByEmail(keycloakConfiguration.getRealm(), investment.getUserEmail());

                EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                        .investorName(userName)
                        .investmentAmount(String.valueOf(investment.getTotalAmount()))
                        .investmentDuration(String.valueOf(investment.getStripping()))
                        .companyName(applicationName)
                        .sharePrice(String.valueOf(partPrice))
                        .scpiName(investment.getScpi().getName())
                        .iban(investment.getScpi().getIban())
                        .bic(investment.getScpi().getBic())
                        .numberOfShares(String.valueOf(investment.getNumberOfShares()))
                        .propertyType(investment.getPropertyType().name())
                        .emailType(REMINDER_AFTER_TWO_DAYS)
                        .build();

                notificationClient.sendEmail(investment.getUserEmail(), "Validation de l'opération - Test", emailDetailsDto);

            });
        }
    }

    private float extractLastPriceOfScpi(List<Price> prices) {
        return prices.stream()
                .map(Price::getPrice)
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("No prices found for SCPI"));
    }

}
