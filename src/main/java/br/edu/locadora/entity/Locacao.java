package br.edu.locadora.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locacao {
    private Long id;
    private Long clienteId;
    private Long filmeId;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
}
