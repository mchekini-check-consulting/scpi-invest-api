package net.checkconsulting.scpiinvestapi.repository;

import net.checkconsulting.scpiinvestapi.entity.PlanifiedInvestment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PlanifiedInvestmentRepository extends JpaRepository<PlanifiedInvestment, Integer> {

    Optional<PlanifiedInvestment> findByLabel(String label);
}
