package net.checkconsulting.scpiinvestapi.repository;

import net.checkconsulting.scpiinvestapi.dto.ComparatorDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScpiRepository extends JpaRepository<Scpi, Integer> {

    @Query("""
            SELECT s FROM Scpi s 
            join Localization loc on s.id = loc.id.scpiId 
            join Sector sec on s.id = sec.id.scpiId
            WHERE (:searchTerm IS NULL OR s.name ILIKE %:searchTerm%)
            AND (:amount = 0 OR s.minimumSubscription <= :amount)
            AND ( :localizations IS NULL OR loc.id.country IN :localizations )
            And (:sectors IS NULL OR sec.id.sector IN :sectors )
            AND ( :fees IS NULL OR
                        (:fees = TRUE AND (s.subscriptionFees > 0 OR s.subscriptionFees IS NULL)) OR
                        (:fees = FALSE AND (s.subscriptionFees = 0 OR s.subscriptionFees IS NULL))
                   ) 
             OR (:searchTerm IS NULL AND :amount = 0 AND :localizations IS NULL AND :sectors IS NULL AND :fees IS NULL)
            """)
    List<Scpi> searchScpiad(String searchTerm,
                           double amount,
                            Boolean fees,
                            List<String> localizations,
                            List<String> sectors
                          );

    List<Scpi> findByName(String name);

    List<Scpi> findByIdIn(List<Integer> ids);

//    @Query("""
//    SELECT s.name AS name,
//           (:investValue * d.distributionRate / 100) / 12 AS rendementMensuel,
//           (:investValue * s.subscriptionFees / 100) AS fraisDeSouscription,
//           (:investValue * 0.04) AS cashback,
//           s.capitalization AS capitalization,
//           s.rentFrequency AS rentFrequency,
//           s.delayBenefit AS delayBenefit
//    FROM Scpi s
//    JOIN DistributionRate d ON s.id = d.id.scpiId
//    WHERE s.id IN :id
//    """)
//    List<ComparatorDto> getInfoScpiForComparison(List<Integer> id,
//                                                 double investValue);
}
