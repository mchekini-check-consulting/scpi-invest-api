package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.PlanifiedInvestmentDto;
import net.checkconsulting.scpiinvestapi.entity.PlanifiedInvestment;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import net.checkconsulting.scpiinvestapi.repository.PlanifiedInvestmentRepository;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlanifiedInvestmentService {

    private final PlanifiedInvestmentRepository planifiedInvestmentRepository;
    private final UserService userService;
    private final ScpiRepository scpiRepository;

    public PlanifiedInvestmentService(PlanifiedInvestmentRepository planifiedInvestmentRepository, UserService userService, ScpiRepository scpiRepository) {
        this.planifiedInvestmentRepository = planifiedInvestmentRepository;
        this.userService = userService;
        this.scpiRepository = scpiRepository;
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

        return planifiedInvestmentRepository.save(planifiedInvestment).getId();

    }
}
