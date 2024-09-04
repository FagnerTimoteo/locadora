package br.edu.locadora.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.edu.locadora.relationship.Locacao;

public interface LocacaoRepository extends CrudRepository<Locacao, Long> {
    List<Locacao> findByClienteId(Long clienteId);
    List<Locacao> findByFilmeId(Long filmeId);
}
