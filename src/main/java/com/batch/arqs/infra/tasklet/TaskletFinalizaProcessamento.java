package com.batch.arqs.infra.tasklet;

import com.batch.arqs.config.batch.SchedulerConfig;
import com.batch.arqs.infra.constants.ApplicationConstants;
import com.batch.arqs.infra.storage.BucketStorage;
import com.batch.arqs.infra.storage.LocalStorage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskletFinalizaProcessamento implements Tasklet, InitializingBean {

    @Value("${common.bucketName}")
    private String bucketName;

    @Value("${common.bucketPath}")
    private String bucketPath;

    @Value("${common.sourcePathLogsbucket}")
    private String pathLogs;

    @Autowired
    private LocalStorage localStorage;

    @Autowired
    private BucketStorage bucketStorage;

    public static final Logger LOGGER = LogManager.getLogger(TaskletFinalizaProcessamento.class);
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOGGER.info("---> Enviando arquivos de logs de processamento para o Bucket");
        var listFiles = localStorage.listArqs();
        listFiles.forEach(arq -> {
            if(arq.contains("ERRO_PROCESSAMENTO")){
                bucketStorage.enviaArqBucket(bucketName, pathLogs, arq);
            }
        });
        LOGGER.info("---> Exluindo arquivos em localhost");
        listFiles.forEach(arq -> {
            localStorage.deletaArquivo(arq);
        });
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
