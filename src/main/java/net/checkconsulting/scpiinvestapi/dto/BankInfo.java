package net.checkconsulting.scpiinvestapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankInfo {
    private String iban;
    private String bic;
    private Float total;
    private String label;
}