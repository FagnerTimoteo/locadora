package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.edu.locadora.DAO.FilmeDAO;
import br.edu.locadora.DTO.FilmeDTO;
import br.edu.locadora.entity.Filme;

@Service
public class FilmeService {

    @Autowired
    private FilmeDAO filmeDAO;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Long generateId() {
        return redisTemplate.opsForValue().increment("filme:id");
    }

    // Método para salvar um filme
    public FilmeDTO save(FilmeDTO filmeDTO) {
        Long id = generateId();

        Filme filme = new Filme(id, filmeDTO.getTitulo(), filmeDTO.getGenero(), filmeDTO.isDisponivel());

        filme = filmeDAO.save(filme);

        return new FilmeDTO(filme.getId(), filme.getTitulo(), filme.getGenero(), filme.isDisponivel());
    }
    
    public Optional<FilmeDTO> findById(Long id) {
        Optional<Filme> filme = filmeDAO.findById(id);
        return filme.map(f -> new FilmeDTO(f.getId(), f.getTitulo(), f.getGenero(), f.isDisponivel()));
    }
    public Optional<FilmeDTO> findByTitulo(String titulo) {
        Optional<Filme> filme = filmeDAO.findByTitulo(titulo);
        return filme.map(f -> new FilmeDTO(f.getId(), f.getTitulo(), f.getGenero(), f.isDisponivel()));
    }
    
    public FilmeDTO update(FilmeDTO filmeDTO) {
        Filme filme = new Filme(filmeDTO.getId(), filmeDTO.getTitulo(), filmeDTO.getGenero(), filmeDTO.isDisponivel());
        Filme updatedFilme = filmeDAO.update(filmeDTO.getId(), filme);
        return new FilmeDTO(updatedFilme.getId(), updatedFilme.getTitulo(), updatedFilme.getGenero(), updatedFilme.isDisponivel());
    }

    public void deleteById(Long id) {
        filmeDAO.deleteById(id);
    }
}
