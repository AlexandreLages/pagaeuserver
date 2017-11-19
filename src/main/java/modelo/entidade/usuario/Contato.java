package modelo.entidade.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.entidade.DefaultEntidade;

@Entity
public class Contato extends DefaultEntidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9173107696339147177L;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String telefone;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private double saldo = 0.0;
	
	@Column(nullable = false)
	private String imagem;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
}