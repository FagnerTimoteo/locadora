package br.edu.locadora.DAO.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import br.edu.locadora.DAO.FilmeDAO;
import br.edu.locadora.entity.Filme;
import br.edu.locadora.repository.FilmeRepository;

@Component
public class FilmeDAOImpl implements FilmeDAO {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String FILME_TITULO = "titulo:";

    @Override
    public Filme save(Filme filme) {
        Filme savedFilme = filmeRepository.save(filme);
        redisTemplate.opsForValue().set(FILME_TITULO + savedFilme.getTitulo(), savedFilme.getId());
        return savedFilme;
    }

    @Override
    public Optional<Filme> findById(Long id) {
        return filmeRepository.findById(id);
    }

    @Override
    public Optional<Filme> findByTitulo(String titulo) {
        String Id = (String) redisTemplate.opsForValue().get(FILME_TITULO + titulo);
        if (Id != null) {
            return filmeRepository.findById(Long.valueOf(Id));
        }
        return Optional.empty();
    }

    @Override
    public Filme update(Long id, Filme filme) {
        if (filmeRepository.existsById(id)) {
            Filme updatedFilme = filmeRepository.save(filme);
            redisTemplate.opsForValue().set(FILME_TITULO + updatedFilme.getTitulo(), updatedFilme.getId());
            return updatedFilme;
        }else {
        	return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        filmeRepository.deleteById(id);
    }
}
