package com.batch.arqs.infra.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocalStorage {

    @Value("${common.sourcePath}")
    private String sourcePath;

    public static final Logger LOGGER = LogManager.getLogger(LocalStorage.class);

    public List<String> listArqs() {
        List<String> arquivos = new ArrayList<>();
        try {
            File[] fileList = new File(sourcePath).listFiles();
            assert fileList != null;
            for(File value : fileList) {
                arquivos.add(value.getName());
            }
        } catch (Exception e) {
            LOGGER.error("---> Não há arquivos para listar.");
        }
        return arquivos;
    }

    public void deletaArquivo(String arquivo) {
        try {
            File file = new File(sourcePath + "/" + arquivo);
            file.delete();
            LOGGER.info(" - " + arquivo + " excluído em localhost.");
        } catch (Exception e) {
            LOGGER.error(" - Não foi possível deletar o arquivo " + arquivo);
        }
    }
}
