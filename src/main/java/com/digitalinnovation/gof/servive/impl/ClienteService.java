package com.digitalinnovation.gof.servive.impl;

import com.digitalinnovation.gof.model.Cliente;
import com.digitalinnovation.gof.model.Endereco;
import com.digitalinnovation.gof.repository.ClienteRepository;
import com.digitalinnovation.gof.repository.EnderecoRepository;
import com.digitalinnovation.gof.servive.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService implements com.digitalinnovation.gof.servive.ClienteService {
    //Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    //Strategy: Implementar os metodos definidos na interface.
    //Facade: Abstrair integracoes com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        //Buscar o Cliente por ID, caso nao exista.
        Optional<Cliente> clienteDb = clienteRepository.findById(id);
        if (clienteDb.isPresent()){
            //Verificar se o Endereco do Cliente ja existe (pelo CEP)
            //Caso nao exista, integrar com o ViaCEP e persistir o retorno.
            //Alterar o Cliente, vinculando o enderco (novo ou existente).
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        //Pesquisar Cliente por id, caso nao exista.
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        var cep = cliente.getEndereco().getCep();
        Endereco enderco = enderecoRepository.findById(cep).orElseGet(() -> {
            //Caso nao exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        cliente.setEndereco(enderco);
        // Inserir Cliente, vinculando o enderco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
