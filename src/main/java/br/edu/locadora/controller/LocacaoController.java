package br.edu.locadora.controller;

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

import br.edu.locadora.DTO.LocacaoDTO;
import br.edu.locadora.service.LocacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;
    
    // Create
    @PostMapping
    @CacheEvict(value="locacoes", allEntries = true)
    public ResponseEntity<LocacaoDTO> create(@Valid @RequestBody LocacaoDTO locacaoDTO) {
        LocacaoDTO novaLocacao = locacaoService.save(locacaoDTO);
        return ResponseEntity.ok(novaLocacao);
    }
    
    // Read
    @GetMapping("/{id}")
    @Cacheable(value="locacoes")
    public ResponseEntity<LocacaoDTO> findById(@PathVariable Long id) {
        LocacaoDTO locacao = locacaoService.findById(id);
        if (locacao != null) {
            return ResponseEntity.ok(locacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Update
    @PutMapping("/{id}")
    @CacheEvict(value="locacoes", allEntries = true)
    public ResponseEntity<LocacaoDTO> update(@Valid @PathVariable Long id, @RequestBody LocacaoDTO locacaoDTO) {
        LocacaoDTO existingLocacao = locacaoService.findById(id);

        if (existingLocacao != null) {
            locacaoDTO.setId(id);
            LocacaoDTO updatedLocacao = locacaoService.update(locacaoDTO);
            
            return ResponseEntity.ok(updatedLocacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    @CacheEvict(value="locacoes", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
