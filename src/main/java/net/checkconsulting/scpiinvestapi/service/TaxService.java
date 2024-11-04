package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.TaxDto;
import net.checkconsulting.scpiinvestapi.dto.TaxInDto;
import net.checkconsulting.scpiinvestapi.entity.Tax;
import net.checkconsulting.scpiinvestapi.entity.UserPreference;
import net.checkconsulting.scpiinvestapi.enums.FamilySituation;
import net.checkconsulting.scpiinvestapi.repository.ImpotRepository;
import net.checkconsulting.scpiinvestapi.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.checkconsulting.scpiinvestapi.enums.FamilySituation.MARIE;
import static net.checkconsulting.scpiinvestapi.utils.Constants.PLAFONNEMENT_QUOTIENT_FAMILIAL;
import static net.checkconsulting.scpiinvestapi.utils.Constants.SOCIAL_DEBIT;

@Service
public class TaxService {

    private final ImpotRepository impotRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final UserService userService;

    public TaxService(ImpotRepository impotRepository, UserPreferenceRepository userPreferenceRepository, UserService userService) {
        this.impotRepository = impotRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.userService = userService;
    }


    public TaxDto calculateScpiTax(TaxInDto taxDto) {

        UserPreference userPreference = userPreferenceRepository.findById(userService.getEmail()).get();

        float percentInFR = (float) (taxDto.getPercentInvestmentInFrance()) / 100;
        float percentInUE = (float) (100 - taxDto.getPercentInvestmentInFrance()) / 100;

        TaxDto taxAmountWithoutScpi = calculateTax(userPreference.getIncome(),
                userPreference.getChildrenCount(), userPreference.getFamilyStatus());

        Integer taxAmountWithScpiInFrance = calculateTax((long) (userPreference.getIncome() + (taxDto.getAnnualScpiIncome() * percentInFR)),
                userPreference.getChildrenCount(), userPreference.getFamilyStatus()).getAmount();



        Integer taxAmountWithScpiInEU = (int) ((taxAmountWithoutScpi.getTmi() - taxAmountWithoutScpi.getAverageRate()) * taxDto.getAnnualScpiIncome() * percentInUE  ) /100;

        Integer socialDebitInFrance = (int) (taxDto.getAnnualScpiIncome() * percentInFR * SOCIAL_DEBIT / 100);

        return TaxDto.builder()
                .amount((taxAmountWithScpiInFrance - taxAmountWithoutScpi.getAmount()) + taxAmountWithScpiInEU + socialDebitInFrance )
                .tmi(taxAmountWithoutScpi.getTmi())
                .averageRate(taxAmountWithoutScpi.getAverageRate())
                .build();

    }


    public TaxDto calculateTax(Long annualIncome, Integer nbChildren, FamilySituation familySituation) {

        double nbParts = calclulateNumberOfParts(familySituation, nbChildren);

        TaxDto taxDto = calculateSimpleTax((long) (annualIncome / nbParts));
        Integer impotBrut = (int) (taxDto.getAmount() * nbParts);
        Integer plafonnementQuotientFamilial = 0;
        double amountWithoutChildren = 0;
        if (nbChildren > 0) {

            if (familySituation == MARIE) {
                amountWithoutChildren = calculateSimpleTax((long) (annualIncome / 2)).getAmount() * 2;
            } else {
                amountWithoutChildren = calculateSimpleTax((long) (annualIncome)).getAmount();
            }

            if (amountWithoutChildren - impotBrut > calclulateNumberOfPartsOfChildren(nbChildren) * PLAFONNEMENT_QUOTIENT_FAMILIAL) {
                plafonnementQuotientFamilial = (int) (amountWithoutChildren - impotBrut - (calclulateNumberOfPartsOfChildren(nbChildren) * PLAFONNEMENT_QUOTIENT_FAMILIAL));
            }

            taxDto.setAmount(impotBrut + plafonnementQuotientFamilial);
            taxDto.setAverageRate(((float) taxDto.getAmount() / annualIncome) * 100);


        }
        return taxDto;

    }

    private double calclulateNumberOfParts(FamilySituation familySituation, Integer nbChildren) {
        double nbParts = 1;
        if (familySituation == MARIE) nbParts = 2;
        nbParts = nbParts + (nbChildren * 0.5);
        if (nbChildren >= 3) nbParts = nbParts + (nbChildren - 2) * 0.5;
        return nbParts;
    }

    private double calclulateNumberOfPartsOfChildren(Integer nbChildren) {
        double nbParts = 0;
        nbParts = nbParts + (nbChildren);
        if (nbChildren >= 3) nbParts = nbParts + (nbChildren - 2);
        return nbParts;
    }

    private TaxDto calculateSimpleTax(Long amount) {

        float totalTax = 0;
        int tmi = 0;
        List<Tax> taxScale = impotRepository.findAll();

        for (int i = 0; i < taxScale.size(); i++) {

            long minRangeValue = taxScale.get(i).getMinRangeValue();
            long maxRangeValue = taxScale.get(i).getMaxRangeValue();

            if (maxRangeValue < amount)
                totalTax = totalTax + ((maxRangeValue - minRangeValue) * (taxScale.get(i).getRate())) / 100;
            else {
                totalTax = totalTax + ((amount - minRangeValue) * (taxScale.get(i).getRate())) / 100;
                tmi = taxScale.get(i).getRate();
                break;
            }

        }


        return TaxDto.builder()
                .amount(Math.round(totalTax))
                .tmi(tmi)
                .build();

    }


}
