package net.checkconsulting.scpiinvestapi.feignClients;

import net.checkconsulting.scpiinvestapi.dto.InvestmentInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "scpi-invest-partner", url = "${partner-service-url}")
public interface InvestmentInfo {
    @PostMapping("/api/v1/investments/create")
    String sendInvestmentInfo (@RequestBody InvestmentInfoDto investmentInfoDto);
}