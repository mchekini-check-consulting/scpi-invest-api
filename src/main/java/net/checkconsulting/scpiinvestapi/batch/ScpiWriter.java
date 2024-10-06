package net.checkconsulting.scpiinvestapi.batch;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.entity.Scpi;
import net.checkconsulting.scpiinvestapi.repository.ScpiRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScpiWriter implements ItemWriter<Scpi> {

    private final ScpiRepository scpiRepository;

    public ScpiWriter(ScpiRepository scpiRepository) {
        this.scpiRepository = scpiRepository;
    }

    @Override
    public void write(Chunk<? extends Scpi> chunk) throws Exception {
        scpiRepository.saveAll(chunk.getItems());
    }
}
