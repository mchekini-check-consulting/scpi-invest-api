package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.PremiumPlanDto;
import net.checkconsulting.scpiinvestapi.entity.PremiumPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PremiumPlanMapper {

    PremiumPlanDto toPremiumPlanDto(PremiumPlan premiumPlan);

    PremiumPlan toPremiumPlan(PremiumPlanDto premiumPlanDto);
}
