package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.ClienteDTO;
import br.edu.locadora.entity.Cliente;
import br.edu.locadora.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }

    public Optional<ClienteDTO> findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(c -> new ClienteDTO(c.getId(), c.getNome(), c.getEmail()));
    }

    public Optional<ClienteDTO> findByNome(String nome) {
        Optional<Cliente> cliente = clienteRepository.findByNome(nome);
        return cliente.map(c -> new ClienteDTO(c.getId(), c.getNome(), c.getEmail()));
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}
