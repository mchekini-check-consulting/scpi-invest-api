package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.*;
import net.checkconsulting.scpiinvestapi.entity.Investment;
import net.checkconsulting.scpiinvestapi.entity.Price;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import net.checkconsulting.scpiinvestapi.enums.PropertyType;
import net.checkconsulting.scpiinvestapi.feignClients.NotificationClient;
import net.checkconsulting.scpiinvestapi.mapper.InvestmentMapper;
import net.checkconsulting.scpiinvestapi.repository.InvestmentRepository;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class InvestmentService {

    @Value("{application.name}")
    String applicationName;

    private final InvestmentRepository investmentRepository;
    private final UserService userService;
    private final ScpiRepository scpiRepository;
    private final NotificationClient notificationClient;
    private final InvestmentMapper investmentMapper;


    public InvestmentService(InvestmentRepository investmentRepository, UserService userService, ScpiRepository scpiRepository, NotificationClient notificationClient, InvestmentMapper investmentMapper) {
        this.investmentRepository = investmentRepository;
        this.userService = userService;
        this.scpiRepository = scpiRepository;
        this.notificationClient = notificationClient;
        this.investmentMapper = investmentMapper;
    }

    public BankInfo createInvestment(InvestmentDtoIn invest) {
        BankInfo response = new BankInfo();

        Optional<Scpi> scpiOptional = scpiRepository.findById(invest.getScpiId());

        if (scpiOptional.isPresent()) {
            Scpi scpi = scpiOptional.get();
            float partPrice = extractLastPriceOfScpi(scpi.getPrices());
            float totalAmount = calculateTotalAmount(invest.getNumberOfShares(), partPrice);

            Investment investment = Investment.builder()
                    .scpi(scpi)
                    .investmentStatus(InvestStatus.PENDING)
                    .propertyType(extractPropertyType(invest.getPropertyType()))
                    .userEmail(userService.getEmail())
                    .totalAmount(totalAmount)
                    .requestDate(LocalDateTime.now())
                    .numberOfShares(invest.getNumberOfShares())
                    .notified(false)
                    .stripping(invest.getStripping())
                    .build();

            Investment savedInvest = investmentRepository.save(investment);

            EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                    .investorName(userService.getUsername())
                    .investmentAmount(String.valueOf(totalAmount))
                    .investmentDuration(String.valueOf(invest.getStripping()))
                    .companyName(applicationName)
                    .sharePrice(String.valueOf(partPrice))
                    .scpiName(scpi.getName())
                    .iban(scpi.getIban())
                    .bic(scpi.getBic())
                    .numberOfShares(String.valueOf(invest.getNumberOfShares()))
                    .propertyType(investment.getPropertyType().name())
                    .build();

            notificationClient.sendEmail(userService.getEmail(), "Validation de l'opération - Test", emailDetailsDto);

            // Update the notified field (Store in Bdd)
            savedInvest.setNotified(true);
            investmentRepository.save(savedInvest);

            response.setBic(scpi.getBic());
            response.setIban(scpi.getIban());
            response.setLabel("SCPI-INVEST-" + savedInvest.getId());
            response.setTotal(totalAmount);

        } else {
            throw new NoSuchElementException("SCPI with id " + invest.getScpiId() + " not found");
        }

        return response;
    }

    private float extractLastPriceOfScpi(List<Price> prices) {
        return prices.stream()
                .map(Price::getPrice)
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("No prices found for SCPI"));
    }

    private PropertyType extractPropertyType(String type) {
        return PropertyType.valueOf(type.toUpperCase());
    }

    private float calculateTotalAmount(Integer numberOfShares, float partPrice) {
        return numberOfShares * partPrice;
    }


    public PortfolioPerformanceDto getPortfolioPerformance() {


        Map<String, Integer> localizations = investmentRepository.getPortfolioLocalizationsByUser(userService.getEmail())
                .stream()
                .collect(Collectors.toMap(
                        map -> String.valueOf(map.get("country")),
                        map -> map.get("percent").intValue()
                ));

        Map<String, Integer> sectors = investmentRepository.getPortfolioSectorsByUser(userService.getEmail())
                .stream()
                .collect(Collectors.toMap(
                        map -> String.valueOf(map.get("sector")),
                        map -> map.get("percent").intValue()
                ));

        Map<String, Map<Integer, Float>> partPrices = investmentRepository.findByUserEmail((userService.getEmail()))
                .stream()
                .map(Investment::getScpi)
                .distinct()
                .collect(Collectors.toMap(
                        Scpi::getName,
                        scpi -> scpi.getPrices().stream()
                                .collect(Collectors.toMap(price -> price.getId().getYear(), Price::getPrice))
                ));


        return PortfolioPerformanceDto.builder()
                .localizations(localizations)
                .sectors(sectors)
                .partPrices(partPrices)
                .build();

    }

    Map.Entry<String, Integer> mapToEntry(Map<String, Double> map) {
        return Map.entry(String.valueOf(map.get("country")), map.get("percent").intValue());
    }

    public List<InvestmentOutDto> getUserInvestments() {
        List<InvestmentOutDto> investmentOutDtos = investmentRepository.findByUserEmail(userService.getEmail()).stream().map(investmentMapper::mapToInvestmentOutDto).toList();
        return investmentOutDtos;
    }
}