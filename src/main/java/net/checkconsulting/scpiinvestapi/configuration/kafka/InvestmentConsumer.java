package net.checkconsulting.scpiinvestapi.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import net.checkconsulting.scpiinvestapi.repository.InvestmentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("!test")
public class InvestmentConsumer {

    private final InvestmentRepository investmentRepository;

    public InvestmentConsumer(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    @KafkaListener(topics = "investments-status", groupId = "groupe-1")
    public void processInvestmentMessage(InvestmentMessage data){

        investmentRepository.findByLibelle(data.getLabel()).ifPresent( investment -> {
            investment.setInvestmentStatus(InvestStatus.valueOf(data.getStatus()));
            investment.setStatusChangeDate(data.getDecisionDate());
            investment.setReason(data.getReason());
            investmentRepository.save(investment);
            log.info("Le statut de l'investissement numéro : {} a été mis à jour avec succes, nouveau statut = {}, date de décision = {} "
                    , investment.getId(), investment.getInvestmentStatus(), investment.getStatusChangeDate());
        });

    }
}