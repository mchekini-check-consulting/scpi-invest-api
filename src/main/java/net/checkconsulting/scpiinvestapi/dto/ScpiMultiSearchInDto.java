package net.checkconsulting.scpiinvestapi.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScpiMultiSearchInDto {
    @Nullable
    private String searchTerm;
    @Nullable
    private List<String> localizations;
    @Nullable
    private List<String> sectors;
    @Nullable
    private double amount;
    @Nullable
    private Boolean fees;
}