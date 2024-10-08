package net.checkconsulting.scpiinvestapi.repository;

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
                        (:fees = TRUE AND (s.managementFees > 0 OR s.managementFees IS NULL)) OR
                        (:fees = FALSE AND (s.managementFees = 0 OR s.managementFees IS NULL))
                   ) 
             OR (:searchTerm IS NULL AND :amount = 0 AND :localizations IS NULL AND :sectors IS NULL AND :fees IS NULL)
            """)
    List<Scpi> searchScpiad(String searchTerm,
                           double amount,
                            Boolean fees,
                            List<String> localizations,
                            List<String> sectors
                          );
}
