package br.edu.locadora.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.locadora.entity.Locacao;

public interface LocacaoRepository extends CrudRepository<Locacao, Long> {
    
}
