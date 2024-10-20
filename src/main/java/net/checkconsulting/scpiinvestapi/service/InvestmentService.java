package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.BankInfo;
import net.checkconsulting.scpiinvestapi.dto.EmailDetailsDto;
import net.checkconsulting.scpiinvestapi.dto.InvestmentDtoIn;
import net.checkconsulting.scpiinvestapi.entity.Investment;
import net.checkconsulting.scpiinvestapi.entity.Price;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.enums.InvestStatus;
import net.checkconsulting.scpiinvestapi.enums.PropertyType;
import net.checkconsulting.scpiinvestapi.feignClients.NotificationClient;
import net.checkconsulting.scpiinvestapi.repository.InvestmentRepository;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class InvestmentService {

    @Value("{application.name}")
    String applicationName;

    private final InvestmentRepository investmentRepository;
    private final UserService userService;
    private final ScpiRepository scpiRepository;
    private final NotificationClient notificationClient;



    public InvestmentService(InvestmentRepository investmentRepository, UserService userService, ScpiRepository scpiRepository, NotificationClient notificationClient) {
        this.investmentRepository = investmentRepository;
        this.userService = userService;
        this.scpiRepository = scpiRepository;
        this.notificationClient = notificationClient;
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

                notificationClient.sendEmail(userService.getEmail(), "Validation de l'opération - Test",emailDetailsDto);


                response.setBic(scpi.getBic());
                response.setIban(scpi.getIban());
                response.setLabel("SCPI-INVEST-"+savedInvest.getId());
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



    public Map<String, Object> getPortfolioPerformance() {
        String userEmail = userService.getEmail();
        List<Investment> investments = investmentRepository.findByUserEmail(userEmail);

        if (investments.isEmpty()) {
            throw new RuntimeException("Pas de portfolio pour cet utilisateur:" + userEmail);
        }

        double totalInvestment = investments.stream()
                .mapToDouble(Investment::getTotalAmount)
                .sum();

        Map<String, Double> geographicalDistribution = new HashMap<>();
        Map<String, Double> sectorDistribution = new HashMap<>();

        for (Investment investment : investments) {
            String country = investment.getScpi().getLocalizations().get(0).getId().getCountry();
            double amount = investment.getTotalAmount();
            geographicalDistribution.put(country, geographicalDistribution.getOrDefault(country, 0.0) + amount);
            String sector = investment.getPropertyType().name();
            sectorDistribution.put(sector, sectorDistribution.getOrDefault(sector, 0.0) + investment.getTotalAmount());
        }
        List<Map<String, Object>> geographicalPercentage = convertToPercentage(geographicalDistribution, totalInvestment);
        List<Map<String, Object>> sectorPercentage = convertToPercentage(sectorDistribution, totalInvestment);
        Map<String, Object> performanceData = new LinkedHashMap<>();
        performanceData.put("pays", geographicalPercentage);
        performanceData.put("secteur", sectorPercentage);
        performanceData.put("évolution du prix de la part", getPriceEvolution(investments));
        return performanceData;
    }
    private List<Map<String, Object>> convertToPercentage(Map<String, Double> distribution, double total) {
        List<Map<String, Object>> percentageList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : distribution.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("nom", entry.getKey());
            map.put("pourcentage", (entry.getValue() / total) * 100);
            percentageList.add(map);
        }
        return percentageList;
    }
    private List<Map<String, Object>> getPriceEvolution(List<Investment> investments) {
        List<Map<String, Object>> priceEvolution = new ArrayList<>();

        for (Investment investment : investments) {
//            String scpiName = investment.getScpi().getName();

            investment.getScpi().getPrices().forEach(price -> {
                Map<String, Object> priceData = new HashMap<>();
                priceData.put("année", price.getId().getYear());
                priceData.put("prix de la part", price.getPrice());
                priceEvolution.add(priceData);
            });
        }
        return priceEvolution;
    }
}