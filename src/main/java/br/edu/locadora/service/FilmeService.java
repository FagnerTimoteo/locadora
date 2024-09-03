package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.ClienteDTO;
import br.edu.locadora.controller.FilmeDTO;
import br.edu.locadora.entity.Cliente;
import br.edu.locadora.entity.Filme;
import br.edu.locadora.repository.ClienteRepository;
import br.edu.locadora.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
    private FilmeRepository filmeRepository;

    public FilmeDTO save(FilmeDTO filmeDTO) {
    	//Instancia um cliente com os valores passados para o dto
        Filme filme = new Filme(filmeDTO.getId(), );
        
        //Salva esse cliente no banco de dados
        filme = filmeRepository.save(filme);
        
        //Retorna um dto com os exatos valores que estão com o cliente não dto
        return new FilmeDTO(filme.getId(), );
    }

    public Optional<FilmeDTO> findById(Long id) {
    	
    	//Acha o cliente no Redis pela id
        Optional<Filme> filme = filmeRepository.findById(id);
        
        //Retorna um por meio de um dicionario um dto equivalente? 
        return filme.map(f -> new FilmeDTO(f.getId(), ));
    }

    public Optional<FilmeDTO> findByNome(String nome) {
        Optional<Filme> cliente = filmeRepository.findByNome(nome);
        return cliente.map(f -> new FilmeDTO(f.getId(), ));
    }

    public void deleteById(Long id) {
    	filmeRepository.deleteById(id);
    }
}
