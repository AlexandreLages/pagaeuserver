package modelo.entidade.usuario;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import modelo.entidade.DefaultEntidade;

@Entity
public class Transacao extends DefaultEntidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6410759081487431087L;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_contato")
	private Contato contato;
	 
	@Column(nullable = false)
	private int tipo;
	
	@Column(nullable = false)
	private double valor;
	
	@Column(nullable = false)
	private double saldoAnterior;
	
	@Column(nullable = false)
	private double saldoAtual;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date dataDaTransacao;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(double saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public double getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
}