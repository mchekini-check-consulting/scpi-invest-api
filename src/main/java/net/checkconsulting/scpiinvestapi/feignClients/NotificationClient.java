package net.checkconsulting.scpiinvestapi.feignClients;

import net.checkconsulting.scpiinvestapi.dto.EmailDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "scpi-invest-notification", url = "http://scpi-invest-notification.scpi-invest-prd:8080")

public interface NotificationClient {

    @PostMapping("/email/send")
    String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestBody EmailDetailsDto emailDetailsDto);
}
