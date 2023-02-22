package com.batch.arqs.infra.integration;

import com.batch.arqs.infra.integration.model.DadosProcess;

import java.util.List;

public interface Integration {
    List<DadosProcess> consultaProcessamento();
}
