package br.edu.locadora.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	// Create
    @PostMapping
    public ResponseEntity<FilmeDTO> create(@RequestBody FilmeDTO filmeDTO) {
    	FilmeDTO createdFilme = filmeService.save(filmeDTO);
        return ResponseEntity.ok(createdFilme);
    }

    // Read
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
    
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<FilmeDTO> update(@PathVariable Long id, @RequestBody FilmeDTO filmeDTO){
    	//Por ser um Optional, != null não funciona
    	if (filmeService.findById(id).isPresent()) {
    		filmeDTO.setId(id);
    		FilmeDTO updatedFilme = filmeService.save(filmeDTO);
    		
    		return ResponseEntity.ok(updatedFilme);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @CacheEvict(value="filmes", allEntries = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    	filmeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}