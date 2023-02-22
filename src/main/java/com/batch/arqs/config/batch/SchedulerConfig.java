package com.batch.arqs.config.batch;

import com.batch.arqs.config.bucket.BucketConfig;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Configuration
@EnableScheduling
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchedulerConfig {

    private final JobLauncher jobLauncher;
    private final Job cargaArquivos;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    public static final Logger LOGGER = LogManager.getLogger(SchedulerConfig.class);

    @Scheduled(cron = "${tempo.execucao}")
    public void scheduleByCron() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        LOGGER.info("---> Iniciando Jog Carga de Arquivos ");
        JobParameters jobParameter = new JobParametersBuilder()
                .addString("instance_id", UUID.randomUUID().toString(), true)
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(cargaArquivos, jobParameter);
        LOGGER.info("---> Job Carga Arquivos Finalizado. \n");
    }

}
