package net.checkconsulting.scpiinvestapi.resources;


import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.TaxDto;
import net.checkconsulting.scpiinvestapi.dto.TaxInDto;
import net.checkconsulting.scpiinvestapi.service.TaxService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tax")
@Slf4j
public class TaxResource {

    private final TaxService taxService;

    public TaxResource(TaxService taxService) {
        this.taxService = taxService;
    }


    @PostMapping
    TaxDto calculateTax(@RequestBody TaxInDto taxDto) {
        log.info("Calculate Tax with parameters: {}", taxDto);
        return taxService.calculateScpiTax(taxDto);
    }
}
