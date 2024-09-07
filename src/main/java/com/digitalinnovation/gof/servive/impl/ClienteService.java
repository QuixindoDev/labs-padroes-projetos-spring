package com.digitalinnovation.gof.servive.impl;

import com.digitalinnovation.gof.exceptions.ClienteNaoEncontradoException;
import com.digitalinnovation.gof.exceptions.RequisicaoInvalidaException;
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

        if (id == null)
            throw new RequisicaoInvalidaException();
        else if (cliente.isEmpty())
            throw new ClienteNaoEncontradoException();

        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void deletar(Long id) {
        //Pesquisar Cliente por id, caso nao exista.
        var cliente = clienteRepository.findById(id);
        if (id == null)
            throw new RequisicaoInvalidaException();
        else if (cliente.isEmpty()) {
            throw new ClienteNaoEncontradoException();
        }

        clienteRepository.deleteById(id);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        //Buscar o Cliente por ID, caso nao exista.
        var clienteDb = clienteRepository.findById(id);
        if (id == null)
            throw new ClienteNaoEncontradoException("Precisa digitar um id valido");
        else if (clienteDb.isEmpty())
            throw new ClienteNaoEncontradoException();

        salvarClienteComCep(cliente);
    }

    public boolean isCepValido(String cep) {
        return cep != null && cep.matches("\\d{8}");
    }

    private void salvarClienteComCep(Cliente cliente) {
        var cep = cliente.getEndereco().getCep();
        if (!isCepValido(cep))
            throw new ClienteNaoEncontradoException("O CEP fornecido é inválido");

        Endereco enderco = enderecoRepository.findById(cep).orElseGet(() -> {
            //Caso nao exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);

            if (novoEndereco == null || novoEndereco.getCep() == null || novoEndereco.getCep().isEmpty())
                throw new ClienteNaoEncontradoException("Não foi possível encontrar um endereço válido para o CEP fornecido");

            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        cliente.setEndereco(enderco);
        // Inserir Cliente, vinculando o enderco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
