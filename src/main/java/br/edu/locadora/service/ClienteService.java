package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.edu.locadora.DAO.ClienteDAO;
import br.edu.locadora.DTO.ClienteDTO;
import br.edu.locadora.entity.Cliente;

@Service
public class ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Método para gerar ID sequencial usando Redis
    public Long generateId() {
        return redisTemplate.opsForValue().increment("cliente:id");
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        Long id = generateId();//Obtem o id gerado em ordem
        //Cria o cliente com os parametros passados
        Cliente cliente = new Cliente(id, clienteDTO.getNome(), clienteDTO.getEmail());
        // Salva o cliente no DAO que por sua vez salva-o no Redis
        cliente = clienteDAO.save(cliente);       
        // Retornar DTO com valores do cliente salvo
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }
    
    public Optional<ClienteDTO> findById(Long id) {
        Optional<Cliente> cliente = clienteDAO.findById(id);
        return cliente.map(c -> new ClienteDTO(c.getId(), c.getNome(), c.getEmail()));
    }

    public Optional<ClienteDTO> findByNome(String nome) {
    	Optional<Cliente> cliente = clienteDAO.findByNome(nome);
        return cliente.map(c -> new ClienteDTO(c.getId(), c.getNome(), c.getEmail()));
        
    }
    
    public ClienteDTO update(ClienteDTO clienteDTO) {
    	Cliente cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
       	Cliente updatedCliente = clienteDAO.update(clienteDTO.getId(), cliente);
        return new ClienteDTO(updatedCliente.getId(), updatedCliente.getNome(), updatedCliente.getEmail());
    }

    public void deleteById(Long id) {
        clienteDAO.deleteById(id);
    }
}
