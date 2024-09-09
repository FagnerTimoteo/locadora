package br.edu.locadora.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.locadora.DTO.ClienteDTO;
import br.edu.locadora.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    // Create
    @PostMapping
    @CacheEvict(value="clientes", allEntries = true)
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO createdCliente = clienteService.save(clienteDTO);
        return ResponseEntity.ok(createdCliente);
    }

    // Read
    @GetMapping("/{id}")
    @Cacheable(value="clientes")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        Optional<ClienteDTO> clienteDTO = clienteService.findById(id);
        return clienteDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nome/{nome}")
    @Cacheable(value="clientes")
    public ResponseEntity<ClienteDTO> findByNome(@PathVariable String nome) {
        Optional<ClienteDTO> clienteDTO = clienteService.findByNome(nome);
        return clienteDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Update
    @PutMapping("/{id}")
    @CacheEvict(value="clientes", allEntries = true)
    public ResponseEntity<ClienteDTO> update(@Valid @PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
    	//Por ser um Optional, != null não funciona
    	if (clienteService.findById(id).isPresent()) {// Verifica se o cliente foi encontrado
    		clienteDTO.setId(id);// Define o ID da cliente o ser atualizado
    		ClienteDTO updatedCliente = clienteService.update(clienteDTO);// Salva o cliente atualizado

    		// Retorna o cliente atualizado com status 200 OK
    		return ResponseEntity.ok(updatedCliente);
        } else {
        	// Retorna 404 se ele não for encontrado
            return ResponseEntity.notFound().build();
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    @CacheEvict(value="clientes", allEntries = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
