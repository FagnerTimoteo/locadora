package br.edu.locadora.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.LocacaoDTO;
import br.edu.locadora.relationship.Locacao;
import br.edu.locadora.repository.LocacaoRepository;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public Long generateId() {
        return redisTemplate.opsForValue().increment("cliente:id");
    }
    
    // Salva uma locação
    public LocacaoDTO save(LocacaoDTO locacaoDTO) {
    	Long id = generateId();
        Locacao locacao = new Locacao(
                id,
                locacaoDTO.getClienteId(),
                locacaoDTO.getFilmeId(),
                locacaoDTO.getDataLocacao(),
                locacaoDTO.getDataDevolucao(),
                locacaoDTO.getValor()
        );
    	
        locacao = locacaoRepository.save(locacao);
        return convertToDTO(locacao);
    }

    // Retorna todas as locações como DTOs
    public List<LocacaoDTO> findAll() {
        return ((Collection<Locacao>) locacaoRepository.findAll())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca uma locação por ID
    public LocacaoDTO findById(Long id) {
        Optional<Locacao> locacao = locacaoRepository.findById(id);
        return locacao.map(this::convertToDTO).orElse(null);
    }
    
    // Busca locações por ID do filme
    public List<LocacaoDTO> findByFilmeId(Long filmeId) {
        return locacaoRepository.findByFilmeId(filmeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca locações por ID do cliente
    public List<LocacaoDTO> findByClienteId(Long clienteId) {
        return locacaoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Deleta uma locação por ID
    public void delete(Long id) {
        locacaoRepository.deleteById(id);
    }

    // Converte uma entidade Locacao para um DTO
    private LocacaoDTO convertToDTO(Locacao locacao) {
        return new LocacaoDTO(
                locacao.getId(),
                locacao.getClienteId(),
                locacao.getFilmeId(),
                locacao.getDataLocacao(),
                locacao.getDataDevolucao(),
                locacao.getValor()
        );
    }
}
