package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.LocacaoDTO;
import br.edu.locadora.DAO.LocacaoDAO;
import br.edu.locadora.relationship.Locacao;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoDAO locacaoDAO;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public Long generateId() {
        return redisTemplate.opsForValue().increment("locacao:id");
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
        locacao = locacaoDAO.save(locacao);
        return convertToDTO(locacao);
    }
    
    // Busca uma locação por ID
    public LocacaoDTO findById(Long id) {
        Optional<Locacao> locacao = locacaoDAO.findById(id);
        return locacao.map(this::convertToDTO).orElse(null);
    }
    
    /*
    // Retorna todas as locações como DTOs
    public List<LocacaoDTO> findAll() {
        return locacaoDAO.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    
    // Busca locações por ID do filme
    public List<LocacaoDTO> findByFilmeId(Long filmeId) {
        return locacaoDAO.findByFilmeId(filmeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
	

    // Busca locações por ID do cliente
    public List<LocacaoDTO> findByClienteId(Long clienteId) {
        return locacaoDAO.findByClienteId(clienteId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    */
    public LocacaoDTO update(LocacaoDTO locacaoDTO) {
        Locacao locacao = new Locacao(
                locacaoDTO.getId(),
                locacaoDTO.getClienteId(),
                locacaoDTO.getFilmeId(),
                locacaoDTO.getDataLocacao(),
                locacaoDTO.getDataDevolucao(),
                locacaoDTO.getValor()
        );
        locacao = locacaoDAO.update(locacao.getId(), locacao);
        return convertToDTO(locacao);
    }

    // Deleta uma locação por ID
    public void delete(Long id) {
        locacaoDAO.deleteById(id);
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
