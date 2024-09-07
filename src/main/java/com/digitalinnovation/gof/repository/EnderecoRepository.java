package com.digitalinnovation.gof.repository;

import com.digitalinnovation.gof.model.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, String> {
}
