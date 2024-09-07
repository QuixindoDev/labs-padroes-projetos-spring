package com.digitalinnovation.gof.servive.impl;

import com.digitalinnovation.gof.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements com.digitalinnovation.gof.servive.ClienteService {
    //TODO: Singleton: Injetar os componentes do Spring com @Autowired.
    //TODO: Strategy: Implementar os metodos definidos na interface.
    //TODO: Facade: Abstrair integracoes com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Cliente> buscarTodos() {
        // FIXME: buscar todos os clientes
        return null;
    }

    @Override
    public Cliente buscarPorId(Long Id) {
        //FIXME: buscar Cliente por ID
        return null;
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        // FIXME: Buscar o Cliente por ID, caso nao exista;
        //FIXME: Verificar se o Endereco do Cliente ja existe (pelo CEP)
        // FIXME: Caso nao exista, integrar com o ViaCEP e persistir o retorno;
        // FIXME: Alterar o Cliente, vinculando o enderco (novo ou existente).
    }

    @Override
    public void inserir(Cliente cliente) {
        // FIXME: Verifier se o endereco do cliente ja existe;
        // FIXME: Caso nao exista, integrar com o ViaCEP e persistir o retorno;
        // FIXME: Inserir Cliente, vinculando o enderco (novo ou existente).
    }

    @Override
    public void deletar(Long id) {
        // FIXME: Pesquisar Cliente por id, caso nao exista.
    }
}
