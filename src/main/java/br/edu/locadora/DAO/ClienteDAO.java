package br.edu.locadora.DAO;

import java.util.Optional;

import br.edu.locadora.entity.Cliente;

public interface ClienteDAO {
	Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByNome(String nome);
    Cliente update(Long id, Cliente cliente);
    void deleteById(Long id);
}
