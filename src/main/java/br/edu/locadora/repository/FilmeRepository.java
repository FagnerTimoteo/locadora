package br.edu.locadora.repository;


import org.springframework.data.repository.CrudRepository;
import br.edu.locadora.entity.Filme;

public interface FilmeRepository extends CrudRepository<Filme, Long>{
	
}
