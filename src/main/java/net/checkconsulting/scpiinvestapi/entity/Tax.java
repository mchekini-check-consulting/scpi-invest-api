package net.checkconsulting.scpiinvestapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Tax {

    @Id
    private Integer id;
    private Long minRangeValue;
    private Long maxRangeValue;
    private Integer rate;
}
