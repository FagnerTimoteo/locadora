package br.edu.locadora.DAO.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import br.edu.locadora.DAO.ClienteDAO;
import br.edu.locadora.entity.Cliente;
import br.edu.locadora.repository.ClienteRepository;

@Component
public class ClienteDAOImpl implements ClienteDAO {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CLIENTE_NOME = "nome:";
    //private static final String CLIENTE_NOME = "titulo:";

    @Override
    public Cliente save(Cliente cliente) {
        // Salva o cliente no repositório
        Cliente savedCliente = clienteRepository.save(cliente);

        // Atualiza o cache Redis
        redisTemplate.opsForValue().set(CLIENTE_NOME + savedCliente.getNome(), savedCliente.getId());

        return savedCliente;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        // Busca o cliente no repositório
        return clienteRepository.findById(id);
    }

    @Override
    public Optional<Cliente> findByNome(String nome) {
        // Busca o ID associado ao nome no Redis
        String clienteId = (String) redisTemplate.opsForValue().get(CLIENTE_NOME + nome);

        if (clienteId != null) {
            // Se o ID foi encontrado, busca o cliente pelo ID
            return clienteRepository.findById(Long.valueOf(clienteId));
        }

        return Optional.empty();
    }

    @Override
    public Cliente update(Long id, Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            // Atualiza o cliente no repositório
            Cliente updatedCliente = clienteRepository.save(cliente);

            // Atualiza o cache Redis
            redisTemplate.opsForValue().set(CLIENTE_NOME + updatedCliente.getNome(), updatedCliente.getId());

            return updatedCliente;
        } else {
            return null;
        	//throw new EntityNotFoundException("Cliente não encontrado");
        }
    }

    @Override
    public void deleteById(Long id) {
        // Deleta o cliente do repositório
        clienteRepository.deleteById(id);

        // Remove o nome do cache Redis
        redisTemplate.opsForValue().getOperations().delete(CLIENTE_NOME + id);
    }
}

