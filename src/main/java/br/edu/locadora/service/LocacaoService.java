package br.edu.locadora.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.locadora.DTO.LocacaoDTO;
import br.edu.locadora.relationship.Locacao;
import br.edu.locadora.repository.LocacaoRepository;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    public List<LocacaoDTO> findAll() {
        return ((List<Locacao>) locacaoRepository.findAll())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LocacaoDTO findById(Long id) {
        Optional<Locacao> locacao = locacaoRepository.findById(id);
        return locacao.map(this::convertToDTO).orElse(null);
    }

    public List<LocacaoDTO> findByClienteId(Long clienteId) {
        return locacaoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LocacaoDTO save(LocacaoDTO locacaoDTO) {
        Locacao locacao = convertToEntity(locacaoDTO);
        locacao = locacaoRepository.save(locacao);
        return convertToDTO(locacao);
    }

    public void delete(Long id) {
        locacaoRepository.deleteById(id);
    }

    private LocacaoDTO convertToDTO(Locacao locacao) {
        return new LocacaoDTO(locacao.getId(), locacao.getClienteId(), locacao.getFilmeId(),
                locacao.getDataLocacao(), locacao.getDataDevolucao(), locacao.getValor());
    }

    private Locacao convertToEntity(LocacaoDTO locacaoDTO) {
        Locacao locacao = new Locacao();
        locacao.setId(locacaoDTO.getId());
        locacao.setClienteId(locacaoDTO.getClienteId());
        locacao.setFilmeId(locacaoDTO.getFilmeId());
        locacao.setDataLocacao(locacaoDTO.getDataLocacao());
        locacao.setDataDevolucao(locacaoDTO.getDataDevolucao());
        locacao.setValor(locacaoDTO.getValor());
        return locacao;
    }
}
