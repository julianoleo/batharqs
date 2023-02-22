package com.batch.arqs.infra.tasklet;

import com.batch.arqs.infra.constants.ApplicationConstants;
import com.batch.arqs.infra.storage.BucketStorage;
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

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskletProcessaArqsInicial implements Tasklet, InitializingBean {

    @Value("${arq.dados}")
    private String arqDados;

    @Value("${common.bucketName}")
    private String bucketName;

    @Value("${common.bucketPath}")
    private String bucketPath;

    private final BucketStorage bucketStorage;

    public static final Logger LOGGER = LogManager.getLogger(TaskletProcessaArqsInicial.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOGGER.info("---> Iniciando Processamento dos Arquivos no Bucket: " + bucketName);
        baixaArqsBucket();
        LOGGER.info("---> Finalizando Processamento dos Arquivos no Bucket: " + bucketName);
        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private void baixaArqsBucket() {
        LOGGER.info("---> Baixando arquivos do Bucket ...");
        var arquivos = ApplicationConstants.getArquivos();
        arquivos.forEach(arq -> {
            bucketStorage.baixaArquivosBucket(bucketName, bucketPath, "/" + arq);
        });
    }
}
