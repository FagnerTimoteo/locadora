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

@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;
    
    // Create
    @PostMapping
    @CacheEvict(value="locacoes", allEntries = true)
    public ResponseEntity<LocacaoDTO> create(@RequestBody LocacaoDTO locacaoDTO) {
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
    
    /*
    @GetMapping("/filme/{filmeId}")
    public ResponseEntity<List<LocacaoDTO>> getByFilmeId(@PathVariable Long filmeId) {
        List<LocacaoDTO> locacoes = locacaoService.findByFilmeId(filmeId);
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<LocacaoDTO>> getByClienteId(@PathVariable Long clienteId) {
        List<LocacaoDTO> locacoes = locacaoService.findByClienteId(clienteId);
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping
    public ResponseEntity<List<LocacaoDTO>> findAll() {
        List<LocacaoDTO> locacoes = locacaoService.findAll();
        return ResponseEntity.ok(locacoes);
    }
    */
    
    // Update
    @PutMapping("/{id}")
    @CacheEvict(value="locacoes", allEntries = true)
    public ResponseEntity<LocacaoDTO> update(@PathVariable Long id, @RequestBody LocacaoDTO locacaoDTO) {
        LocacaoDTO existingLocacao = locacaoService.findById(id);  // Obtém a locação existente

        if (existingLocacao != null) {  // Verifica se a locação foi encontrada
            locacaoDTO.setId(id);  // Define o ID da locação a ser atualizada
            LocacaoDTO updatedLocacao = locacaoService.update(locacaoDTO);  // Salva a locação atualizada
            
            return ResponseEntity.ok(updatedLocacao);  // Retorna a locação atualizada com status 200 (OK)
        } else {
            return ResponseEntity.notFound().build();  // Retorna 404 se a locação não for encontrada
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
