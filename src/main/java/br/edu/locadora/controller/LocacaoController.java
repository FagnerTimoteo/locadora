package br.edu.locadora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public ResponseEntity<List<LocacaoDTO>> getAllLocacoes() {
        List<LocacaoDTO> locacoes = locacaoService.findAll();
        return ResponseEntity.ok(locacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocacaoDTO> getLocacaoById(@PathVariable Long id) {
        LocacaoDTO locacao = locacaoService.findById(id);
        if (locacao != null) {
            return ResponseEntity.ok(locacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<LocacaoDTO>> getLocacoesByClienteId(@PathVariable Long clienteId) {
        List<LocacaoDTO> locacoes = locacaoService.findByClienteId(clienteId);
        return ResponseEntity.ok(locacoes);
    }

    @PostMapping
    public ResponseEntity<LocacaoDTO> createLocacao(@RequestBody LocacaoDTO locacaoDTO) {
        LocacaoDTO novaLocacao = locacaoService.save(locacaoDTO);
        return ResponseEntity.ok(novaLocacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocacao(@PathVariable Long id) {
        locacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
