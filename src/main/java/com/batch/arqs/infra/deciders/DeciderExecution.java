package com.batch.arqs.infra.deciders;

import com.batch.arqs.config.bucket.BucketConfig;
import com.batch.arqs.infra.integration.Integration;
import com.batch.arqs.infra.integration.model.DadosProcess;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeciderExecution {

    private final Integration integration;

    public static final Logger LOGGER = LogManager.getLogger(DeciderExecution.class);

    public List<DadosProcess> execute() {
        LOGGER.info("---> Verificando a existencia de arquivos a serem processados.");
        return integration.consultaProcessamento();
    }

}
