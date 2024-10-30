package net.checkconsulting.scpiinvestapi.service;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.PremiumPlanDto;
import net.checkconsulting.scpiinvestapi.entity.PremiumPlan;
import net.checkconsulting.scpiinvestapi.mapper.PremiumPlanMapper;
import net.checkconsulting.scpiinvestapi.repository.PremiumPlanRepository;
import net.checkconsulting.scpiinvestapi.utils.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PremiumPlanService {

    private final PremiumPlanRepository premiumPlanRepository;
    private final PremiumPlanMapper premiumPlanMapper;

    public PremiumPlanService(PremiumPlanRepository premiumPlanRepository, PremiumPlanMapper premiumPlanMapper) {
        this.premiumPlanRepository = premiumPlanRepository;
        this.premiumPlanMapper = premiumPlanMapper;
    }

    public List<PremiumPlanDto> getPremiumPlans() {
       return  premiumPlanRepository.findAll().stream().map(premiumPlanMapper::toPremiumPlanDto).toList();
    }

    public ApiResponse<PremiumPlanDto> updatePremiumPlan(PremiumPlanDto premiumPlanDto) {
        try {
            Optional<PremiumPlan> optionalPlan = premiumPlanRepository.findById(premiumPlanDto.getId());

            if (optionalPlan.isPresent()) {
                PremiumPlan plan = optionalPlan.get();

                plan.setStandard(premiumPlanDto.getStandard()!=null?premiumPlanDto.getStandard():plan.getStandard());
                plan.setPremium(premiumPlanDto.getPremium()!=null?premiumPlanDto.getPremium():plan.getPremium());
                plan.setDescription(premiumPlanDto.getDescription()!=null?premiumPlanDto.getDescription():plan.getDescription());
                PremiumPlan updatedPlan = premiumPlanRepository.save(plan);

                log.info("Premium plan updated successfully {}", updatedPlan);
                return new ApiResponse<>("Premium plan updated successfully", premiumPlanMapper.toPremiumPlanDto(updatedPlan));

            } else {
                log.info("Premium plan not found with id {}", premiumPlanDto.getId());
                return new ApiResponse<>("Premium plan not found", null);
            }
        } catch (Exception e) {
            log.error("An error occurred while updating the premium plan : {}",e.getMessage());
            return new ApiResponse<>("An error occurred while updating the premium plan",
                    List.of(new ApiResponse.ErrorDetail("updateError", e.getMessage())));
        }
    }
}
