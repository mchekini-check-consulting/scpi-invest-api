package net.checkconsulting.scpiinvestapi.resources;


import net.checkconsulting.scpiinvestapi.dto.TaxDto;
import net.checkconsulting.scpiinvestapi.dto.TaxInDto;
import net.checkconsulting.scpiinvestapi.service.TaxService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tax")
public class TaxResource {

    private final TaxService taxService;

    public TaxResource(TaxService taxService) {
        this.taxService = taxService;
    }


    @PostMapping
    TaxDto calculateTax(@RequestBody TaxInDto taxDto) {
        return taxService.calculateScpiTax(taxDto);
    }
}
