package br.edu.locadora.DAO;

import java.util.Optional;

import br.edu.locadora.relationship.Locacao;

public interface LocacaoDAO {
    Locacao save(Locacao locacao);
    Optional<Locacao> findById(Long id);
    Locacao update(Long id, Locacao locacao);
    void deleteById(Long id);
}
