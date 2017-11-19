package modelo.entidade.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import modelo.entidade.DefaultEntidade;

@Entity
public class Usuario extends DefaultEntidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8214408802643745102L;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String telefone;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	@OneToMany(mappedBy = "usuario", targetEntity = Contato.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Contato> contatos = new ArrayList<Contato>();
	

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
}