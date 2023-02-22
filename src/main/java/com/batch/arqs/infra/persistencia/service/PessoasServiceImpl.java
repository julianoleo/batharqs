package com.batch.arqs.infra.persistencia.service;

import com.batch.arqs.infra.persistencia.models.Pessoas;
import com.batch.arqs.infra.persistencia.repository.PessoasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoasServiceImpl implements PessoasService {

    @Autowired
    private PessoasRepository pessoasRepository;

    @Override
    public void incluiPessoas(Pessoas pessoas) {
        pessoasRepository.save(pessoas);
    }
}
