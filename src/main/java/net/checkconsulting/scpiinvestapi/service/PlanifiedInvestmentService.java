package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.PartnerPlannedInvestDto;
import net.checkconsulting.scpiinvestapi.dto.PlanifiedInvestmentDto;
import net.checkconsulting.scpiinvestapi.dto.PlannedInvestementEmailDto;
import net.checkconsulting.scpiinvestapi.entity.PlanifiedInvestment;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import net.checkconsulting.scpiinvestapi.enums.PropertyType;
import net.checkconsulting.scpiinvestapi.feignClients.InvestmentInfo;
import net.checkconsulting.scpiinvestapi.feignClients.NotificationClient;
import net.checkconsulting.scpiinvestapi.repository.PlanifiedInvestmentRepository;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static net.checkconsulting.scpiinvestapi.utils.Constants.APPLICATION_CODE;

@Service
public class PlanifiedInvestmentService {

    private final PlanifiedInvestmentRepository planifiedInvestmentRepository;
    private final UserService userService;
    private final ScpiRepository scpiRepository;
    private final NotificationClient notificationClient;
    private final InvestmentInfo investmentInfo;

    public PlanifiedInvestmentService(PlanifiedInvestmentRepository planifiedInvestmentRepository, UserService userService, ScpiRepository scpiRepository, NotificationClient notificationClient, InvestmentInfo investmentInfo) {
        this.planifiedInvestmentRepository = planifiedInvestmentRepository;
        this.userService = userService;
        this.scpiRepository = scpiRepository;
        this.notificationClient = notificationClient;
        this.investmentInfo = investmentInfo;
    }


    public Integer createPlannifiedInvestment(PlanifiedInvestmentDto planifiedInvestmentDto) {

        PlanifiedInvestment planifiedInvestment = PlanifiedInvestment
                .builder()
                .requestDate(LocalDateTime.now())
                .debitDayOfMonth(planifiedInvestmentDto.getDebitDayOfMonth())
                .status(InvestStatus.PENDING)
                .userEmail(userService.getEmail())
                .numberOfShares(planifiedInvestmentDto.getNumberOfShares())
                .frequency(planifiedInvestmentDto.getFrequency())
                .scpi(scpiRepository.findById(planifiedInvestmentDto.getScpi()).get())
                .amount(planifiedInvestmentDto.getAmount())
                .build();

        Integer planifiedInvestement= planifiedInvestmentRepository.save(planifiedInvestment).getId();

        planifiedInvestment.setLabel(APPLICATION_CODE + "-" + planifiedInvestment.getId());
         planifiedInvestmentRepository.save(planifiedInvestment).getId();

        PartnerPlannedInvestDto partnerPlannedInvestDto = PartnerPlannedInvestDto.builder()
                .email(userService.getEmail())
                .firstName(userService.getFirstName())
                .lastName(userService.getLastName())
                .frequency(planifiedInvestment.getFrequency().name())
                .debitDayOfMonth(planifiedInvestment.getDebitDayOfMonth())
                .amount(planifiedInvestment.getAmount())
                .numberOfShares(planifiedInvestment.getNumberOfShares())
                .propertyType(PropertyType.PLEINE_PROPRIETE.name())
                .label(APPLICATION_CODE + "-" + planifiedInvestment.getId())
                .build();
        investmentInfo.sendPlannedInvestement(partnerPlannedInvestDto);
        PlannedInvestementEmailDto plannedInvestementEmailDto =  PlannedInvestementEmailDto.builder()
                .investorName(userService.getUsername())
                .plannedInvestmentId(String.valueOf(planifiedInvestement))
                .build();

        notificationClient.sendEmailPlannedInvest(userService.getEmail(), "Confirmation de votre demande de planification de versement",plannedInvestementEmailDto);

        return planifiedInvestement;

    }
}
