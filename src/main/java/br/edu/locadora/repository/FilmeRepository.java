package br.edu.locadora.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.locadora.entity.Filme;

@Repository
public interface FilmeRepository extends CrudRepository<Filme, Long> {
    Optional<Filme> findByTitulo(String titulo);
}