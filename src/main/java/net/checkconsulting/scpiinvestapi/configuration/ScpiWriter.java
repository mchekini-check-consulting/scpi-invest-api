package net.checkconsulting.scpiinvestapi.configuration;

import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.scpiinvestapi.Entity.Scpi;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScpiWriter implements ItemWriter<Scpi> {
    @Override
    public void write(Chunk<? extends Scpi> chunk) throws Exception {
      log.info("Je suis passer par le Writer");
    }
}
