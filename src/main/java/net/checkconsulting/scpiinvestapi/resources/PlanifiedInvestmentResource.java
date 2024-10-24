package net.checkconsulting.scpiinvestapi.resources;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.PlanifiedInvestmentDto;
import net.checkconsulting.scpiinvestapi.service.PlanifiedInvestmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/planified-investement")
@Slf4j
public class PlanifiedInvestmentResource {

    private final PlanifiedInvestmentService planifiedInvestmentService;

    public PlanifiedInvestmentResource(PlanifiedInvestmentService planifiedInvestmentService) {
        this.planifiedInvestmentService = planifiedInvestmentService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plannified Investment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "SCPI not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity createPlanifiedInvestment(@RequestBody PlanifiedInvestmentDto planifiedInvestmentDto) {


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/planified-investement/" + planifiedInvestmentService.createPlannifiedInvestment(planifiedInvestmentDto));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


}
