package br.edu.locadora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.LocacaoDTO;
import br.edu.locadora.entity.Locacao;
import br.edu.locadora.DAO.LocacaoDAO;

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
    @CacheEvict(value="locacoes", allEntries = true)
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
    @Cacheable(value="locacoes")
    public LocacaoDTO findById(Long id) {
        Optional<Locacao> locacao = locacaoDAO.findById(id);
        return locacao.map(this::convertToDTO).orElse(null);
    }
    
    // Atualiza uma locação
    @CacheEvict(value="locacoes", allEntries = true)
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
    @CacheEvict(value="locacoes", allEntries = true)
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
