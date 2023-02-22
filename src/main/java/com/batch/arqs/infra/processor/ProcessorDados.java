package com.batch.arqs.infra.processor;

import com.batch.arqs.infra.models.ObjetoEntrada;
import com.batch.arqs.infra.models.ObjetoSaida;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@StepScope
public class ProcessorDados implements ItemProcessor<ObjetoEntrada, ObjetoSaida> {

    @Value("#{StepExecution}")
    public StepExecution stepExecution;

    @PostConstruct
    void init() throws JsonProcessingException { }

    public static final Logger LOGGER = LogManager.getLogger(ProcessorDados.class);

    @Override
    public ObjetoSaida process(ObjetoEntrada objetoEntrada) throws Exception {
        ObjetoSaida objetoSaida = new ObjetoSaida();
        try {
            var result =  ObjetoSaida.builder()
                    .linha(objetoEntrada.getLinha())
                    .nomePai(objetoEntrada.getNomePai())
                    .nomeMae(objetoEntrada.getNomeMae())
                    .nome(objetoEntrada.getNome())
                    .arquivo(objetoEntrada.getArquivo())
                    .dadosObjeto(objetoEntrada)
                    .build();
            objetoSaida = result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return objetoSaida;
    }
}
