package com.batch.arqs.infra.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ObjetoSaida {
    public ObjetoSaida() {  }

    ObjetoEntrada dadosObjeto;
    String linha;
    String arquivo;
    String nomeMae;
    String nome;
    String nomePai;
    String logErro;

    public ObjetoSaida(ObjetoEntrada dadosObjeto, String linha, String arquivo, String nomeMae, String nome, String nomePai, String logErro) {
        this.dadosObjeto = dadosObjeto;
        this.linha = linha;
        this.arquivo = arquivo;
        this.nomeMae = nomeMae;
        this.nome = nome;
        this.nomePai = nomePai;
        this.logErro = logErro;
    }

    @Override
    public String toString() {
        return "{" +
                "dadosObjeto=" + dadosObjeto +
                ", linha='" + linha + '\'' +
                ", arquivo='" + arquivo + '\'' +
                ", nomeMae='" + nomeMae + '\'' +
                ", nome='" + nome + '\'' +
                ", nomePai='" + nomePai + '\'' +
                ", logErro='" + logErro + '\'' +
                '}';
    }
}
