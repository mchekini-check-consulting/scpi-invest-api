package net.checkconsulting.scpiinvestapi.repository;

import net.checkconsulting.scpiinvestapi.entity.PremiumPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumPlanRepository extends JpaRepository<PremiumPlan, Integer> {
}
