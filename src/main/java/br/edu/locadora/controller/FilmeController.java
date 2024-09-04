package br.edu.locadora.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.locadora.DTO.FilmeDTO;
import br.edu.locadora.service.FilmeService;

@RestController
@RequestMapping("/filmes")
public class FilmeController {
	
	@Autowired
    private FilmeService filmeService;

    @PostMapping
    public ResponseEntity<FilmeDTO> create(@RequestBody FilmeDTO clienteDTO) {
    	FilmeDTO createdFilme = filmeService.save(clienteDTO);
        return ResponseEntity.ok(createdFilme);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> findById(@PathVariable Long id) {
        Optional<FilmeDTO> filmeDTO = filmeService.findById(id);
        return filmeDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<FilmeDTO> findByTitulo(@PathVariable String titulo) {
        Optional<FilmeDTO> filmeDTO = filmeService.findByTitulo(titulo);
        return filmeDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    	filmeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}