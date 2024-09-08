package br.edu.locadora.DAO;

import java.util.Optional;

import br.edu.locadora.entity.Filme;

public interface FilmeDAO {
    Filme save(Filme filme);
    Optional<Filme> findById(Long id);
    Optional<Filme> findByTitulo(String titulo);
    Filme update(Long id, Filme filme);
    void deleteById(Long id);
}
