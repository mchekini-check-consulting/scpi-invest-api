package net.checkconsulting.scpiinvestapi.resources;


import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.service.ScpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/scpi")
public class ScpiResource {
    private final ScpiService scpiService;

    public ScpiResource(ScpiService scpiService) {
        this.scpiService = scpiService;
    }

    @GetMapping
    public ResponseEntity<List<ScpiOutDto>> getAllScpi() {
        return ResponseEntity.ok().body(scpiService.findAllScpi());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScpiDetailDto> getScpiById(@PathVariable Integer id) throws Exception {
        ScpiDetailDto scpi = scpiService.findScpiById(id);
        return ResponseEntity.ok(scpi);
    }
}
