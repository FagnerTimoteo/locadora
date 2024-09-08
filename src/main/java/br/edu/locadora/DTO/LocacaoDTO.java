package br.edu.locadora.DTO;

import java.time.LocalDate;

public class LocacaoDTO {
    private Long id;
    private Long clienteId;
    private Long filmeId;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private Double valor;

    public LocacaoDTO() {
    }

    public LocacaoDTO(Long id, Long clienteId, Long filmeId, LocalDate dataLocacao, LocalDate dataDevolucao, Double valor) {
        this.id = id;
        this.clienteId = clienteId;
        this.filmeId = filmeId;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Long filmeId) {
        this.filmeId = filmeId;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
