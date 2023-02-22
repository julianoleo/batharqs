package com.batch.arqs.infra.integration;

import com.batch.arqs.infra.integration.model.DadosProcess;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IntegrationImpl implements Integration {
    @Override
    public List<DadosProcess> consultaProcessamento() {
        var arq1 = DadosProcess.builder()
                .nomeArq("DADOS.TXT")
                .status("Validado")
                .dataInc("20230220")
                .build();

        var arq2 = DadosProcess.builder()
                .nomeArq("DADOS_02.TXT")
                .status("Validado")
                .dataInc("20230220")
                .build();

        var arq3 = DadosProcess.builder()
                .nomeArq("DADOS_TESTE.TXT")
                .status("Validado")
                .dataInc("20230220")
                .build();

        List<DadosProcess> result = new ArrayList<>();
        result.add(arq1);
        result.add(arq2);
        result.add(arq3);
        return result;
    }
}
