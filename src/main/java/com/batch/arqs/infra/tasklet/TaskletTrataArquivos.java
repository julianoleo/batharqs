package com.batch.arqs.infra.tasklet;

import com.batch.arqs.infra.storage.LocalStorage;
import com.batch.arqs.infra.utils.GeraArqs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskletTrataArquivos implements Tasklet, InitializingBean {

    @Value("${common.sourcePath}")
    private String sourcePath;

    @Value("${arq.dados}")
    private String arqDados;

    private final LocalStorage localStorage;
    private final GeraArqs geraArqs;

    public static final Logger LOGGER = LogManager.getLogger(TaskletTrataArquivos.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOGGER.info("---> Iniciando Tratamento dos Arquivos em Localhost. ");
        trataArquivos();
        deletaArqsLocal();
        LOGGER.info("---> Finalizando Tratamento dos Arquivos em Localhost. ");
        return RepeatStatus.FINISHED;
    }

    protected void trataArquivos() throws IOException {
        List<String> arquivos = localStorage.listArqs();
        List<String> dadosArquivo = new ArrayList<>();
        for (String arq : arquivos) {
            BufferedReader br = new BufferedReader(new FileReader(sourcePath.concat("/" + arq)));
            while (br.readLine() != null) {
                dadosArquivo.add(trataLinha(arq, br.readLine()));
            }
            br.close();
        }
        geraArqs.geraArq(arqDados, dadosArquivo, true);
    }

    protected String trataLinha(String nomeArq, String linha) {
        StringBuilder result = new StringBuilder();
        if(linha != null) {
            result.append(StringUtils.rightPad(nomeArq, 50, "") + linha);
        }
        return result.toString();
    }

    protected void deletaArqsLocal() {
        List<String> arquivos = localStorage.listArqs();
        for (String arquivo : arquivos) {
            if(arquivo.contains("DADOS")){
                localStorage.deletaArquivo(arquivo);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
