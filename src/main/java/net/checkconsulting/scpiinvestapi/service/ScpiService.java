package net.checkconsulting.scpiinvestapi.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ComparatorDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiMultiSearchInDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.mapper.ScpiMapper;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ScpiService {
    private final ScpiRepository scpiRepository;
    private final ScpiMapper scpiMapper;

    @Autowired
    public ScpiService(ScpiRepository scpiRepository, ScpiMapper scpiMapper) {
        this.scpiMapper = scpiMapper;
        this.scpiRepository = scpiRepository;
    }

    public List<ScpiOutDto> findAllScpi() {
       List<Scpi> result = scpiRepository.findAll();
     return  result.stream().map(scpiMapper::mapToScpiOutDto).toList();

    }

    public List<ScpiOutDto> findScpiWithFilters(ScpiMultiSearchInDto scpiMultiSearchInDto) {
        List<Scpi> result =  scpiRepository.searchScpiad(
                scpiMultiSearchInDto.getSearchTerm(),
                scpiMultiSearchInDto.getAmount(),
                scpiMultiSearchInDto.getFees(),
                scpiMultiSearchInDto.getLocalizations(),
                scpiMultiSearchInDto.getSectors()
        );
        return  result.stream().map(scpiMapper::mapToScpiOutDto).toList();
    }

    public ScpiDetailDto findScpiById(Integer id) throws Exception {
        Optional<Scpi> optionalScpi = scpiRepository.findById(id);
        if (optionalScpi.isEmpty()) throw new Exception("No SCPI with ID = " + id);

        return scpiMapper.mapToScpiDetailDto(optionalScpi.get());
    }

    public List<ComparatorDto> getInfoScpiForComparison(List<Integer> idList, double investValue) {
        List<Scpi> scpis = scpiRepository.findByIdIn(idList);

        return scpis.stream()
                .map(scpi -> {
                    double rendementMensuel = (investValue * (scpi.getDistributionRate()/ 100)) / 12;
                    double fraisDeSouscription = (investValue * scpi.getSubscriptionFees() / 100);
                    double cashback = investValue * 0.04;

                    return ComparatorDto.builder()
                            .id(scpi.getId())
                            .name(scpi.getName())
                            .monthlyRevenue(rendementMensuel)
                            .subscriptionFees(fraisDeSouscription)
                            .cashback(cashback)
                            .capitalization(scpi.getCapitalization())
                            .rentFrequency(scpi.getRentFrequency())
                            .entryDelay(scpi.getDelayBenefit())
                            .minInvestment(scpi.getMinimumSubscription())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
