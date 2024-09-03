package br.edu.locadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.locadora.DTO.ClienteDTO;
import br.edu.locadora.service.ClienteService;

import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO createdCliente = clienteService.save(clienteDTO);
        return ResponseEntity.ok(createdCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        Optional<ClienteDTO> clienteDTO = clienteService.findById(id);
        return clienteDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ClienteDTO> findByNome(@PathVariable String nome) {
        Optional<ClienteDTO> clienteDTO = clienteService.findByNome(nome);
        return clienteDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
