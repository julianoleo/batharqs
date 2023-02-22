package com.batch.arqs.infra.utils;

import com.batch.arqs.infra.storage.LocalStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class GeraArqs{

    @Autowired
    private LocalStorage localStorage;

    @Value("${common.sourcePath}")
    private String sourcePath;

    @Value("${arq.dados}")
    private String arqDados;

    public static final Logger LOGGER = LogManager.getLogger(GeraArqs.class);

    public void geraArq(String nomeArg, List<String> dados, Boolean incrementa) {
        if(!dados.isEmpty()) {
             File file = new File(sourcePath.concat("/" + nomeArg));
             try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, incrementa)))){
                 for (String linha : dados) {
                     bw.write(linha + "\n");
                 }
                 bw.close();
                 LOGGER.info(" - Arquivo " + file + " gravado com sucesso!");
             } catch (IOException e) {
                 LOGGER.error("Erro na gravação do arquivo " + sourcePath.concat("/" + nomeArg));
             }
        } else {
            LOGGER.error("Não há dados para serem gravados no Arquivo " + nomeArg);
        }
    }
}
