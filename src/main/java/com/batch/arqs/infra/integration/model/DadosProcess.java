package com.batch.arqs.infra.integration.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DadosProcess {

    public DadosProcess() { }

    String nomeArq;
    String status;
    String dataInc;

    public DadosProcess(String nomeArq, String status, String dataInc) {
        this.nomeArq = nomeArq;
        this.status = status;
        this.dataInc = dataInc;
    }

    @Override
    public String toString() {
        return "DadosProcess{" +
                "nomeArq='" + nomeArq + '\'' +
                ", status='" + status + '\'' +
                ", dataInc='" + dataInc + '\'' +
                '}';
    }
}
