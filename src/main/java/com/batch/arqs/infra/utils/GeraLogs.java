package com.batch.arqs.infra.utils;

import com.batch.arqs.infra.models.ObjetoSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeraLogs {

    @Autowired
    private GeraArqs geraArqs;

    public void geraLogs(List<ObjetoSaida> dados) {
        List<String> nomesArgs = new ArrayList<>();
        dados.forEach(dado -> {
            nomesArgs.add(dado.getArquivo());
        });
        List<String> nomesFiltrados = nomesArgs.stream().distinct().collect(Collectors.toList());

        nomesFiltrados.forEach(arq -> {
            List<String> dadosLogs = new ArrayList<>();
            dados.forEach(dado -> {
                if(dado.getArquivo().contains(arq)) {
                    dadosLogs.add(dado.getLinha().substring(50) + dado.getLogErro());
                }
            });
            geraArqs.geraArq("ERRO_PROCESSAMENTO_" + arq, dadosLogs, true);
        });
    }
}
