package br.edu.locadora.entity;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Filme")
public class Filme {
    private Long id;
	private String titulo;
    private String genero;
    private boolean disponivel;
    
    public Filme(Long id, String titulo, String genero, boolean disponivel) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.genero = genero;
		this.disponivel = disponivel;
	}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

}
