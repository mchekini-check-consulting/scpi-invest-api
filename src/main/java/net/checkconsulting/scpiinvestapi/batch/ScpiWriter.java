package net.checkconsulting.scpiinvestapi.batch;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ScpiWriter implements ItemWriter<Scpi> {

    private final ScpiRepository scpiRepository;

    public ScpiWriter(ScpiRepository scpiRepository) {
        this.scpiRepository = scpiRepository;
    }

    @Override
    public void write(Chunk<? extends Scpi> chunk) throws Exception {

        List<Scpi> newScpiToSave = new ArrayList<>();

        for (Scpi scpi : chunk.getItems()){
            List<Scpi> existedScpi = scpiRepository.findByName(scpi.getName());
            scpiRepository.deleteAll(existedScpi);
            newScpiToSave.add(scpi);
        }

        scpiRepository.saveAll(newScpiToSave);
    }
}
