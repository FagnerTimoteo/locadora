package br.edu.locadora.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import br.edu.locadora.entity.Filme;

public interface FilmeRepository extends CrudRepository<Filme, Long>{
	Optional<Filme> findByTitulo(String titulo);
}
