package net.checkconsulting.scpiinvestapi.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.configuration.security.KeycloakConfiguration;
import net.checkconsulting.scpiinvestapi.dto.EmailPlannedInvestPartnerNotificationDto;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import net.checkconsulting.scpiinvestapi.feignClients.NotificationClient;
import net.checkconsulting.scpiinvestapi.repository.InvestmentRepository;
import net.checkconsulting.scpiinvestapi.repository.PlanifiedInvestmentRepository;
import net.checkconsulting.scpiinvestapi.service.KeycloakAdminService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvestmentConsumer {

    private final InvestmentRepository investmentRepository;
    private final PlanifiedInvestmentRepository planifiedInvestmentRepository;
    private final NotificationClient notificationClient;
    private final KeycloakAdminService keycloakAdminService;
    private final KeycloakConfiguration keycloakConfiguration;

    public InvestmentConsumer(InvestmentRepository investmentRepository, PlanifiedInvestmentRepository planifiedInvestmentRepository, NotificationClient notificationClient, KeycloakAdminService keycloakAdminService, KeycloakConfiguration keycloakConfiguration) {
        this.investmentRepository = investmentRepository;
        this.planifiedInvestmentRepository = planifiedInvestmentRepository;
        this.notificationClient = notificationClient;
        this.keycloakAdminService = keycloakAdminService;
        this.keycloakConfiguration = keycloakConfiguration;
    }

    @KafkaListener(topics = "investments-status-${spring.profiles.active}", groupId = "groupe-1")
    public void processInvestmentMessage(InvestmentMessage data){

        log.info("received message from Kafka {}", data);
        investmentRepository.findByLibelle(data.getLabel()).ifPresent( investment -> {
            investment.setInvestmentStatus(InvestStatus.valueOf(data.getStatus()));
            investment.setStatusChangeDate(data.getDecisionDate());
            investment.setReason(data.getReason());
            investmentRepository.save(investment);
            log.info("Le statut de l'investissement numéro : {} a été mis à jour avec succes, nouveau statut = {}, date de décision = {} "
                    , investment.getId(), investment.getInvestmentStatus(), investment.getStatusChangeDate());
        });

    }

    @KafkaListener(topics = "planned-investments-status-${spring.profiles.active}", groupId = "groupe-1")
    public void processPlannedInvestmentMessage(InvestmentMessage data){

        log.info("received message from Kafka {}", data);
        planifiedInvestmentRepository.findByLabel(data.getLabel()).ifPresent( plannedInvestment -> {
            String userName =   keycloakAdminService.getFirstNamByEmail(keycloakConfiguration.getRealm(),plannedInvestment.getUserEmail())+' '+keycloakAdminService.getLastNamByEmail(keycloakConfiguration.getRealm(),plannedInvestment.getUserEmail());

            plannedInvestment.setStatus(InvestStatus.valueOf(data.getStatus()));
            plannedInvestment.setDecisionDate(data.getDecisionDate());
            plannedInvestment.setReason(data.getReason());
            planifiedInvestmentRepository.save(plannedInvestment);

            if (plannedInvestment.getStatus()==InvestStatus.REJECTED) {
                EmailPlannedInvestPartnerNotificationDto rejectInfo = EmailPlannedInvestPartnerNotificationDto.builder()
                        .investorName(userName)
                        .reason(plannedInvestment.getReason())
                        .build();
                notificationClient.sendEmailRejectPlannedInvest(plannedInvestment.getUserEmail(), "Notification de Refus de Demande de Prélèvement Programmée", rejectInfo);
            } else if (plannedInvestment.getStatus()==InvestStatus.VALIDATED) {

            EmailPlannedInvestPartnerNotificationDto validateInfo = EmailPlannedInvestPartnerNotificationDto.builder()
                    .investorName(userName)
                    .amount(plannedInvestment.getAmount())
                    .frequency(plannedInvestment.getFrequency().name())
                    .debitDayOfMonth(plannedInvestment.getDebitDayOfMonth())
                    .build();
            notificationClient.sendEmailValidatePlannedInvest(plannedInvestment.getUserEmail(),"Notification de Validation de Demande de Prélèvement Programmée",validateInfo);
            }
        log.info("Le statut de l'investissement numéro : {} a été mis à jour avec succes, nouveau statut = {}, date de décision = {} "
                    , plannedInvestment.getId(), plannedInvestment.getStatus(), plannedInvestment.getDecisionDate());
        });

    }
}
