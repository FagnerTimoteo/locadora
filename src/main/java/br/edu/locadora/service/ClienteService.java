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
    	//Instancia um cliente com os valores passados para o dto
        Cliente cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
        
        //Salva esse cliente no banco de dados
        cliente = clienteRepository.save(cliente);
        
        //Retorna um dto com os exatos valores que estão com o cliente não dto
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }

    public Optional<ClienteDTO> findById(Long id) {
    	
    	//Acha o cliente no Redis pela id
        Optional<Cliente> cliente = clienteRepository.findById(id);
        
        //Retorna um por meio de um dicionario um dto equivalente? 
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
