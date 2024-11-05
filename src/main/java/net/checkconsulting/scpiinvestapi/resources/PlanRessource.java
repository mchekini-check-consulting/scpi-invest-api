package net.checkconsulting.scpiinvestapi.resources;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.PlanDto;
import net.checkconsulting.scpiinvestapi.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plan")
@Slf4j
public class PlanRessource {
    private final PlanService planService;

    public PlanRessource(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanDto> findAllPlans() {
        log.info("Fetching all plans");
        return planService.getPremiumPlans();
    }

    @PostMapping("/update")
    public PlanDto updatePlan(@RequestBody PlanDto planDto) {
        log.info("Updating plan with ID: {}", planDto.getId());
        return planService.updatePlan(planDto);
    }
}
