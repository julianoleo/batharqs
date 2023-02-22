package com.batch.arqs.infra.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ObjetoEntrada {
    public ObjetoEntrada() {  }

    String linha;
    String arquivo;
    String nomeMae;
    String nome;
    String nomePai;

    public ObjetoEntrada(String linha, String arquivo, String nomeMae, String nome, String nomePai) {
        this.linha = linha;
        this.arquivo = arquivo;
        this.nomeMae = nomeMae;
        this.nome = nome;
        this.nomePai = nomePai;
    }

    @Override
    public String toString() {
        return "{" +
                "linha='" + linha + '\'' +
                ", arquivo='" + arquivo + '\'' +
                ", nomeMae='" + nomeMae + '\'' +
                ", nome='" + nome + '\'' +
                ", nomePai='" + nomePai + '\'' +
                '}';
    }
}
