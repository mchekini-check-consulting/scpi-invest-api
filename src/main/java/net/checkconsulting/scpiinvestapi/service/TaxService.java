package net.checkconsulting.scpiinvestapi.service;

import net.checkconsulting.scpiinvestapi.dto.TaxDto;
import net.checkconsulting.scpiinvestapi.dto.TaxInDto;
import net.checkconsulting.scpiinvestapi.entity.Tax;
import net.checkconsulting.scpiinvestapi.repository.ImpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.checkconsulting.scpiinvestapi.enums.FamilySituation.MARIE;
import static net.checkconsulting.scpiinvestapi.utils.Constants.PLAFONNEMENT_QUOTIENT_FAMILIAL;

@Service
public class TaxService {

    private final ImpotRepository impotRepository;

    public TaxService(ImpotRepository impotRepository) {
        this.impotRepository = impotRepository;
    }


    public TaxDto calculateTax(TaxInDto taxInDto) {

        double nbParts = calclulateNumberOfParts(taxInDto);



        TaxDto taxDto = calculateSimpleTax((long) (taxInDto.getAnnualIncome() / nbParts));
        Integer impotBrut = (int) (taxDto.getAmount() * nbParts);
        Integer plafonnementQuotientFamilial = 0;
        double amountWithoutChildren = 0;
        if (taxInDto.getNumberOfChildren() > 0){

            if (taxInDto.getFamilySituation() == MARIE){
               amountWithoutChildren=  calculateSimpleTax((long) (taxInDto.getAnnualIncome() / 2)).getAmount() * 2;
            }
            else {
                amountWithoutChildren  = calculateSimpleTax((long) (taxInDto.getAnnualIncome())).getAmount();
            }

            if (amountWithoutChildren - impotBrut > calclulateNumberOfPartsOfChildren(taxInDto) * PLAFONNEMENT_QUOTIENT_FAMILIAL ){
                plafonnementQuotientFamilial = (int) (amountWithoutChildren - impotBrut - (calclulateNumberOfPartsOfChildren(taxInDto) * PLAFONNEMENT_QUOTIENT_FAMILIAL));
            }

            taxDto.setAmount(impotBrut + plafonnementQuotientFamilial);
            taxDto.setAverageRate(((float) taxDto.getAmount() / taxInDto.getAnnualIncome()) * 100);


        }
        return taxDto;

    }

    private double calclulateNumberOfParts(TaxInDto taxInDto) {
        double nbParts = 1;
        if (taxInDto.getFamilySituation() == MARIE) nbParts = 2;
        nbParts = nbParts + (taxInDto.getNumberOfChildren() * 0.5);
        if (taxInDto.getNumberOfChildren() >= 3) nbParts = nbParts + (taxInDto.getNumberOfChildren() - 2) * 0.5;
        return nbParts;
    }

    private double calclulateNumberOfPartsOfChildren(TaxInDto taxInDto) {
        double nbParts = 0;
        nbParts = nbParts + (taxInDto.getNumberOfChildren());
        if (taxInDto.getNumberOfChildren() >= 3) nbParts = nbParts + (taxInDto.getNumberOfChildren() - 2);
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
