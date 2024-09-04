package br.edu.locadora.DTO;

public class FilmeDTO {
	private Long id;
	private String titulo;
    private String genero;
    private boolean disponivel;
	
    public FilmeDTO(Long id, String titulo, String genero, boolean disponivel) {
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