package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.ClienteDTO;
import br.edu.locadora.entity.Cliente;
import br.edu.locadora.repository.ClienteRepository;

import org.springframework.data.redis.core.RedisTemplate;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Método para gerar ID sequencial usando Redis
    public Long generateId() {
        return redisTemplate.opsForValue().increment("cliente:id");
    }

    // Método para salvar um cliente no banco de dados
    public ClienteDTO save(ClienteDTO clienteDTO) {
        Long id = generateId();

        // Instancia um cliente com os valores passados para o DTO
        Cliente cliente = new Cliente(id, clienteDTO.getNome(), clienteDTO.getEmail());

        // Salva esse cliente no banco de dados
        cliente = clienteRepository.save(cliente);

        // Retorna um DTO com os exatos valores do cliente salvo
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }

    // Método para encontrar cliente pelo ID
    public Optional<ClienteDTO> findById(Long id) {
        // Acha o cliente no repositório pela ID
        Optional<Cliente> cliente = clienteRepository.findById(id);

        // Mapeia o cliente para um DTO e retorna
        return cliente.map(c -> new ClienteDTO(c.getId(), c.getNome(), c.getEmail()));
    }

    // Método para encontrar cliente pelo nome
    public Optional<ClienteDTO> findByNome(String nome) {
        Optional<Cliente> cliente = clienteRepository.findByNome(nome);
        return cliente.map(c -> new ClienteDTO(c.getId(), c.getNome(), c.getEmail()));
    }

    // Método para deletar cliente pelo ID
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}
