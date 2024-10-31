package net.checkconsulting.scpiinvestapi.repository;

import net.checkconsulting.scpiinvestapi.entity.Investment;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface InvestmentRepository extends JpaRepository<Investment, Integer> {
    @Query("""
            SELECT i FROM Investment i WHERE i.userEmail = :userEmail
            """)
    List<Investment> findByUserEmail(String userEmail);

    @Query("""
            SELECT l.id.country as country, floor(sum((l.percent * i.totalAmount / 100)) /
             (SELECT sum(i2.totalAmount) from Investment i2 where i2.userEmail = :userEmail) * 100) as percent
            from Investment i join Localization l on i.scpi.id = l.id.scpiId
            WHERE i.userEmail = :userEmail
            group by l.id.country
            """)
    List<Map<String, Double>> getPortfolioLocalizationsByUser(String userEmail);


    @Query("""
            SELECT s.id.sector as sector, floor(sum((s.percent * i.totalAmount / 100)) /
             (SELECT sum(i2.totalAmount) from Investment i2 where i2.userEmail = :userEmail) * 100) as percent
            from Investment i join Sector s on i.scpi.id = s.id.scpiId
            WHERE i.userEmail = :userEmail
            group by s.id.sector
            """)
    List<Map<String, Double>> getPortfolioSectorsByUser(String userEmail);


    @EntityGraph(attributePaths = {"scpi", "scpi.prices"})
    List<Investment> findInvestmentByRequestDateIsBetweenAndInvestmentStatusEquals(LocalDateTime startDate, LocalDateTime endDate, InvestStatus investStatus);
}
