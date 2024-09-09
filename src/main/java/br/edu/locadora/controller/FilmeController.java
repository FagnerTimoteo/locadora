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

import br.edu.locadora.DTO.FilmeDTO;
import br.edu.locadora.service.FilmeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // Create
    @PostMapping
    @CacheEvict(value="filmes", allEntries = true)
    public ResponseEntity<FilmeDTO> create(@Valid @RequestBody FilmeDTO filmeDTO) {
        FilmeDTO createdFilme = filmeService.save(filmeDTO);
        return ResponseEntity.ok(createdFilme);
    }

    // Read
    @GetMapping("/{id}")
    @Cacheable(value="filmes")
    public ResponseEntity<FilmeDTO> findById(@PathVariable Long id) {
        Optional<FilmeDTO> filmeDTO = filmeService.findById(id);
        return filmeDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/titulo/{titulo}")
    @Cacheable(value="filmes")
    public ResponseEntity<FilmeDTO> findByTitulo(@PathVariable String titulo) {
        Optional<FilmeDTO> filmeDTO = filmeService.findByTitulo(titulo);
        return filmeDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    @CacheEvict(value="filmes", allEntries = true)
    public ResponseEntity<FilmeDTO> update(@Valid  @PathVariable Long id, @RequestBody FilmeDTO filmeDTO) {
        if (filmeService.findById(id).isPresent()) {
            filmeDTO.setId(id);
            FilmeDTO updatedFilme = filmeService.update(filmeDTO);
            return ResponseEntity.ok(updatedFilme);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    @CacheEvict(value="filmes", allEntries = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        filmeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}