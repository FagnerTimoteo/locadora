package br.edu.locadora.DAO.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.locadora.DAO.LocacaoDAO;
import br.edu.locadora.relationship.Locacao;

@Component
public class LocacaoDAOImpl implements LocacaoDAO {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ObjectMapper objectMapper; // Para conversão entre objetos e JSON

    private static final String LOCACAO_PREFIX = "locacao:";

    @Override
    public Locacao save(Locacao locacao) {
        String key = LOCACAO_PREFIX + locacao.getId();
        try {
            String json = objectMapper.writeValueAsString(locacao);
            redisTemplate.opsForValue().set(key, json);
            return locacao;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save Locacao", e);
        }
    }

    @Override
    public Optional<Locacao> findById(Long id) {
        String key = LOCACAO_PREFIX + id;
        String json = (String) redisTemplate.opsForValue().get(key);
        if (json != null) {
            try {
                Locacao locacao = objectMapper.readValue(json, Locacao.class);
                return Optional.of(locacao);
            } catch (Exception e) {
                throw new RuntimeException("Failed to find Locacao by ID", e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Locacao update(Long id, Locacao locacao) {
        return save(locacao); // Save will also handle updates
    }

    @Override
    public void deleteById(Long id) {
        String key = LOCACAO_PREFIX + id;
        redisTemplate.delete(key);
    }
}
