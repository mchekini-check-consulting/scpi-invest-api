package net.checkconsulting.scpiinvestapi.resources;

import net.checkconsulting.scpiinvestapi.dto.PremiumPlanDto;
import net.checkconsulting.scpiinvestapi.service.PremiumPlanService;
import net.checkconsulting.scpiinvestapi.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plan")
public class PremiumPlanRessource {
    private final PremiumPlanService premiumPlanService;

    public PremiumPlanRessource(PremiumPlanService premiumPlanService) {
        this.premiumPlanService = premiumPlanService;
    }

    @GetMapping
    public List<PremiumPlanDto> findAllPremiumPlan() {
        return premiumPlanService.getPremiumPlans();
    }

    @PostMapping("/update")
    public ApiResponse<PremiumPlanDto> updatePremiumPlan(@RequestBody PremiumPlanDto planDto) {
        return premiumPlanService.updatePremiumPlan(planDto);
    }
}
