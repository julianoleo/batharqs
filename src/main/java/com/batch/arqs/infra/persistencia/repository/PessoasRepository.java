package com.batch.arqs.infra.persistencia.repository;

import com.batch.arqs.infra.persistencia.models.Pessoas;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface PessoasRepository extends Repository<Pessoas, UUID>{
    void save(Pessoas pessoas);
}
