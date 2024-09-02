package br.edu.locadora.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filme {
    private Long id;
    private String titulo;
    private String genero;
    private boolean disponivel;
}
