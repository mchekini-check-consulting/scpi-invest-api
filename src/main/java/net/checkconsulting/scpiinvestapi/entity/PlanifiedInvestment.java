package net.checkconsulting.scpiinvestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.checkconsulting.scpiinvestapi.enums.Frequency;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlanifiedInvestment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planified_investment_seq_gen")
    @SequenceGenerator(name = "planified_investment_seq_gen", sequenceName = "planified_investment_seq", allocationSize = 1)
    private Integer id;
    private String userEmail;
    @Enumerated(STRING)
    private Frequency frequency;
    private Float amount;
    private Integer debitDayOfMonth;
    @Enumerated(STRING)
    private InvestStatus status;
    private Integer numberOfShares;
    private LocalDateTime requestDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scpi_id", nullable = false)
    private Scpi scpi;
}
