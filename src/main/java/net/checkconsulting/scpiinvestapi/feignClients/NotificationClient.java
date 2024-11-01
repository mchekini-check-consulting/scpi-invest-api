package net.checkconsulting.scpiinvestapi.feignClients;

import net.checkconsulting.scpiinvestapi.dto.EmailDetailsDto;
import net.checkconsulting.scpiinvestapi.dto.PlannedInvestementEmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "scpi-invest-notification", url = "${notification-service-url}")
public interface NotificationClient {

    @PostMapping("/email/send")
    String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestBody EmailDetailsDto emailDetailsDto);

    @PostMapping("/email/planned-invest")
    String sendEmailPlannedInvest(@RequestParam String to, @RequestParam String subject, @RequestBody PlannedInvestementEmailDto plannedInvestementEmailDto);
}
