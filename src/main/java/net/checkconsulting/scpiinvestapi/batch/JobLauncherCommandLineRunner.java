package net.checkconsulting.scpiinvestapi.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherCommandLineRunner implements CommandLineRunner {


    private final JobLauncher jobLauncher;
    private final Job migrationJob;

    public JobLauncherCommandLineRunner(JobLauncher jobLauncher, Job migrationJob) {
        this.jobLauncher = jobLauncher;
        this.migrationJob = migrationJob;
    }

    @Override
    public void run(String... args) {
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void executeBatchJob() throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(migrationJob, parameters);
    }
}
