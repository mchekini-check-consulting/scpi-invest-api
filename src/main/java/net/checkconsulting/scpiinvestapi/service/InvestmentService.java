package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.entity.Investment;
import net.checkconsulting.scpiinvestapi.repository.InvestmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final UserService userService;

    public InvestmentService(InvestmentRepository investmentRepository, UserService userService) {
        this.investmentRepository = investmentRepository;
        this.userService = userService;
    }

    public Map<String, Object> getPortfolioPerformance() {
        String userId = userService.getId();
        List<Investment> investments = investmentRepository.findByUser_id(userId);

        if (investments.isEmpty()) {
            throw new RuntimeException("Pas de portfolio pour cet utilisateur:" + userId);
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
            String sector = investment.getPropertyType();
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