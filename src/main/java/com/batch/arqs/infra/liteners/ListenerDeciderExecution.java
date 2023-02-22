package com.batch.arqs.infra.liteners;

import com.batch.arqs.config.bucket.BucketConfig;
import com.batch.arqs.infra.constants.ApplicationConstants;
import com.batch.arqs.infra.deciders.DeciderExecution;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListenerDeciderExecution implements StepExecutionListener {

    private final DeciderExecution deciderExecution;

    public static final Logger LOGGER = LogManager.getLogger(ListenerDeciderExecution.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("---> Iniciando decider de execução do processamento...");
        var result = deciderExecution.execute();
        if(result.isEmpty()){
            LOGGER.error("---> Execução ABORTADA por inexistencia de arquivos a serem processados.");
            return ExitStatus.STOPPED;
        } else {
            LOGGER.info("---> Execução iniciada com o processamento dos arquivos: ");
            result.forEach(arq -> {
                ApplicationConstants.setArquivos(arq.getNomeArq());
                LOGGER.info(" - " + arq);
            });
            return stepExecution.getExitStatus();
        }
    }
}
