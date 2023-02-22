package com.batch.arqs.infra.persistencia;

import com.batch.arqs.infra.persistencia.models.Pessoas;
import com.batch.arqs.infra.persistencia.service.PessoasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenciaImpl implements Persistencia{

    @Autowired
    private PessoasService pessoasService;

    @Override
    public void incluiPessoa(Pessoas pessoas) {
        pessoasService.incluiPessoas(pessoas);
    }
}
