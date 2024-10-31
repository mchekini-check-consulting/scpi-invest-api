package net.checkconsulting.scpiinvestapi.service;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.PlanDto;
import net.checkconsulting.scpiinvestapi.entity.Plan;
import net.checkconsulting.scpiinvestapi.mapper.PlanMapper;
import net.checkconsulting.scpiinvestapi.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public PlanService(PlanRepository planRepository, PlanMapper planMapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }


    public List<PlanDto> getPremiumPlans() {
        return planRepository.findAll().stream().map(planMapper::toPremiumPlanDto).toList();
    }

    public PlanDto updatePlan(PlanDto premiumPlanDto) {

        Plan plan = planRepository.findById(premiumPlanDto.getId()).get();

        plan.setStandard(premiumPlanDto.getStandard() != null ? premiumPlanDto.getStandard() : plan.getStandard());
        plan.setPremium(premiumPlanDto.getPremium() != null ? premiumPlanDto.getPremium() : plan.getPremium());
        plan.setDescription(premiumPlanDto.getDescription() != null ? premiumPlanDto.getDescription() : plan.getDescription());
        Plan updatedPlan = planRepository.save(plan);

        log.info("Premium plan updated successfully {}", updatedPlan);
        return planMapper.toPremiumPlanDto(updatedPlan);


    }
}
