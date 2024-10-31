package net.checkconsulting.scpiinvestapi.mapper;

import net.checkconsulting.scpiinvestapi.dto.PlanDto;
import net.checkconsulting.scpiinvestapi.entity.Plan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanDto toPremiumPlanDto(Plan premiumPlan);

    Plan toPremiumPlan(PlanDto premiumPlanDto);
}
