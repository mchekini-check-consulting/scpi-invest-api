package net.checkconsulting.scpiinvestapi.repository;

import net.checkconsulting.scpiinvestapi.dto.ScpiComparatorOutDto;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComparisonRepository extends JpaRepository<Scpi, Integer> {


    @Query("""
            SELECT new net.checkconsulting.scpiinvestapi.dto.ScpiComparatorOutDto(
                s.id,
                s.name,
                CAST((:investValue * d.distributionRate / 100) / 12 AS double),
                CAST((:investValue * s.subscriptionFees / 100) AS double),
                CAST((:investValue * 0.04) AS double),
                s.capitalization,
                s.rentFrequency,
                s.delayBenefit,
                s.minimumSubscription
            )
            FROM Scpi s
            JOIN DistributionRate d ON s.id = d.id.scpiId
            WHERE s.name IN :selectedScpis
            """)
    List<ScpiComparatorOutDto> getInfoScpiForComparison(List<String> selectedScpis, double investValue);


}
