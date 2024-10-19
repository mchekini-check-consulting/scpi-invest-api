package net.checkconsulting.scpiinvestapi.repository;

import net.checkconsulting.scpiinvestapi.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment,String> {
@Query("""
            SELECT i FROM Investment i WHERE i.user_email = :userEmail
            """)
    List<Investment> findByUser_email(String userEmail);
}
