package com.digitalinnovation.gof.servive;

import com.digitalinnovation.gof.model.Cliente;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();
    Cliente buscarPorId(Long Id);
    void atualizar(Long id, Cliente cliente);
    void inserir(Cliente cliente);
    void deletar(Long id);
}
