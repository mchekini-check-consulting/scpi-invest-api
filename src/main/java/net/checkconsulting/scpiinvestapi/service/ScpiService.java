package net.checkconsulting.scpiinvestapi.service;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.dto.ScpiDetailDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiInDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiMultiSearchInDto;
import net.checkconsulting.scpiinvestapi.dto.ScpiOutDto;
import net.checkconsulting.scpiinvestapi.entity.*;
import net.checkconsulting.scpiinvestapi.mapper.ScpiMapper;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


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


    public Integer createScpi(ScpiInDto scpiInDto) {
        Scpi scpi = scpiMapper.scpiWithoutRelation(scpiInDto);
        scpi.setLocalizations(scpiMapper.toLocalization(scpiInDto,scpi));
        scpi.setSectors(scpiMapper.toSector(scpiInDto,scpi));
        scpi.setDistributionRate(scpiMapper.toDistributionRate(scpiInDto,scpi));
        scpi.setPrices(scpiMapper.toPrice(scpiInDto,scpi));
        scpi.setDiscountStripping(scpiMapper.toDiscountStripping(scpiInDto,scpi));

        return scpiRepository.save(scpi).getId();
    }

    public List<ScpiOutDto> findAllScpi() {
        List<Scpi> result = scpiRepository.findAll();
        return result.stream().map(scpiMapper::mapToScpiOutDto)
                .sorted(Comparator.comparing(ScpiOutDto::getLastYearDistributionRate).reversed())
                .toList();

    }

    public List<ScpiOutDto> findScpiWithFilters(ScpiMultiSearchInDto scpiMultiSearchInDto) {
        List<Scpi> result = scpiRepository.searchScpiad(
                scpiMultiSearchInDto.getSearchTerm(),
                scpiMultiSearchInDto.getAmount(),
                scpiMultiSearchInDto.getFees(),
                scpiMultiSearchInDto.getLocalizations(),
                scpiMultiSearchInDto.getSectors()
        );
        return result.stream().map(scpiMapper::mapToScpiOutDto).toList();
    }

    public ScpiDetailDto findScpiById(Integer id) throws Exception {
        Optional<Scpi> optionalScpi = scpiRepository.findById(id);
        if (optionalScpi.isEmpty()) throw new Exception("No SCPI with ID = " + id);

        return scpiMapper.mapToScpiDetailDto(optionalScpi.get());
    }
}
