package net.checkconsulting.scpiinvestapi.repository;

import net.checkconsulting.scpiinvestapi.entity.Scpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScpiRepository extends JpaRepository<Scpi, Integer> {
}
