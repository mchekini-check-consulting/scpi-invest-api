package net.checkconsulting.scpiinvestapi.configuration;

import net.checkconsulting.scpiinvestapi.Entity.Scpi;
import net.checkconsulting.scpiinvestapi.dto.ScpiDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
public class BatchConfiguration {

    private final ScpiReader scpiReader;

    private final ScpiProcessor scpiProcessor;

    private final ScpiWriter scpiWriter;


    public BatchConfiguration(ScpiReader scpiReader, ScpiProcessor scpiProcessor, ScpiWriter scpiWriter) {
        this.scpiReader = scpiReader;
        this.scpiProcessor = scpiProcessor;
        this.scpiWriter = scpiWriter;

    }


    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException {

        return new StepBuilder("step", jobRepository)
                .<ScpiDto, Scpi>chunk(1, transactionManager)
                .reader(scpiReader.reader())
                .processor(scpiProcessor)
                .writer(scpiWriter)
                .build();
    }

    @Bean
    public Job migrationJob(JobRepository jobRepository, Step step) {

        Flow flow = new FlowBuilder<SimpleFlow>("Getting data from scpi file Flow")
                .start(step)
                .end();

        return new JobBuilder("ScpiMigrationJob", jobRepository)
                .start(flow)
                .end()
                .build();
    }

}
