package net.checkconsulting.scpiinvestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scpi_seq_gen")
    @SequenceGenerator(name = "scpi_seq_gen", sequenceName = "scpi_seq", allocationSize = 1)

    private Integer id;
    private String user_id;
    private String propertyType;
    private Integer numberOfShares;
    private Float totalAmount;
    private String investmentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scpi_id", nullable = false)
    private Scpi scpi;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;
}
