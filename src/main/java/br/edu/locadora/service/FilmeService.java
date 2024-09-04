package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.FilmeDTO;
import br.edu.locadora.entity.Filme;
import br.edu.locadora.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
    private FilmeRepository filmeRepository;

    public FilmeDTO save(FilmeDTO filmeDTO) {
        Filme filme = new Filme(
        		filmeDTO.getId(), filmeDTO.getTitulo(), filmeDTO.getGenero(), filmeDTO.isDisponivel());
        
        filme = filmeRepository.save(filme);
        
        return new FilmeDTO(
        		filme.getId(), filme.getTitulo(), filme.getGenero(), filme.isDisponivel());
    }

    public Optional<FilmeDTO> findById(Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme.map(f -> new FilmeDTO(f.getId(), f.getTitulo(), f.getGenero(), f.isDisponivel()));
    }

    public Optional<FilmeDTO> findByTitulo(String titulo) {
        Optional<Filme> cliente = filmeRepository.findByTitulo(titulo);
        return cliente.map(f -> new FilmeDTO(f.getId(), f.getTitulo(), f.getGenero(), f.isDisponivel()));
    }

    public void deleteById(Long id) {
    	filmeRepository.deleteById(id);
    }
}
