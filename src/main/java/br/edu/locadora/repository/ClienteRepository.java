package br.edu.locadora.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.locadora.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	// Metodo extra
    Optional<Cliente> findByNome(String nome);//Teste buscar por nome
}
