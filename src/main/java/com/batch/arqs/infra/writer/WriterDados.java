package com.batch.arqs.infra.writer;

import com.batch.arqs.infra.models.ObjetoSaida;
import com.batch.arqs.infra.persistencia.Persistencia;
import com.batch.arqs.infra.persistencia.models.Pessoas;
import com.batch.arqs.infra.utils.GeraLogs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class WriterDados implements ItemWriter {

    @Value("#{StepExecution}")
    public StepExecution stepExecution;

    @Autowired
    private Persistencia persistencia;

    public static final Logger LOGGER = LogManager.getLogger(WriterDados.class);

    private List<ObjetoSaida> dadosLogs = new ArrayList<>();

    @Autowired
    private GeraLogs geraLogs;

    @AfterStep
    private void depoisDaExecucao() {
        geraLogs.geraLogs(dadosLogs);
        LOGGER.info(" - Quantidade de registros incluídos no banco de dados: " + qtdeRegistrosIncluidos);
    }

    private int qtdeRegistrosIncluidos = 0;

    @Override
    public void write(List list) throws Exception {
        List<ObjetoSaida> dados = new ArrayList<>();
        list.forEach(obj -> {
            dados.add((ObjetoSaida) obj);
        });

        for (ObjetoSaida dado : dados) {
            gravaDados(dado);
        }
    }

    protected void gravaDados(ObjetoSaida dado) {
        try {
            if(dado.getArquivo().contains("TESTE")) {
                dado.setLogErro(" [Erro na Inclusão do registro] --> SQLStatus 94522");
                dadosLogs.add(dado);
            } else {
                persistencia.incluiPessoa(
                        Pessoas.builder()
                                .nome(dado.getNome())
                                .pai(dado.getNomePai())
                                .mae(dado.getNomeMae())
                                .build()
                );
                qtdeRegistrosIncluidos++;
            }
            // LOGGER.info(" - Pessoa Incluida com sucesso.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
