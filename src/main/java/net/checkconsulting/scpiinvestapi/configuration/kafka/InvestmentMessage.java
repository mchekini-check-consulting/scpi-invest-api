package net.checkconsulting.scpiinvestapi.configuration.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestmentMessage {

    private String label;
    private String status;
    private LocalDateTime decisionDate;
    private String reason;
}
