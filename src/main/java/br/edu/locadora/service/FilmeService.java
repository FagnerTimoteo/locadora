package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.FilmeDTO;
import br.edu.locadora.entity.Filme;
import br.edu.locadora.repository.FilmeRepository;

@Service
public class FilmeService {
	
	String teste;

	@Autowired
    private FilmeRepository filmeRepository;

	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
    public Long generateId() {
        return redisTemplate.opsForValue().increment("filme:id");
    }
	
    public FilmeDTO save(FilmeDTO filmeDTO) {
    	Long id = generateId();
        
        Filme filme = new Filme(
        		id, filmeDTO.getTitulo(), filmeDTO.getGenero(), filmeDTO.isDisponivel());
        
        filme = filmeRepository.save(filme);
        
        return new FilmeDTO(
        		filme.getId(), filme.getTitulo(), filme.getGenero(), filme.isDisponivel());
    }

    public Optional<FilmeDTO> findById(Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme.map(f -> new FilmeDTO(f.getId(), f.getTitulo(), f.getGenero(), f.isDisponivel()));
    }

    public Optional<FilmeDTO> findByTitulo(String titulo) {
        titulo = titulo.trim(); // Remove espaços em branco no início e no fim
        Optional<Filme> filme = filmeRepository.findByTitulo(titulo);
        
        System.out.println("Título pesquisado: " + titulo);
        
        return filme.map(f -> new FilmeDTO(f.getId(), f.getTitulo(), f.getGenero(), f.isDisponivel()));
    }


    public void deleteById(Long id) {
    	filmeRepository.deleteById(id);
    }
}
