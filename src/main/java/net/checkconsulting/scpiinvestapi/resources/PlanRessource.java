package net.checkconsulting.scpiinvestapi.resources;

import net.checkconsulting.scpiinvestapi.dto.PlanDto;
import net.checkconsulting.scpiinvestapi.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plan")
public class PlanRessource {
    private final PlanService planService;

    public PlanRessource(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanDto> findAllPlans() {
        return planService.getPremiumPlans();
    }

    @PostMapping("/update")
    public PlanDto updatePlan(@RequestBody PlanDto planDto) {
        return planService.updatePlan(planDto);
    }
}
