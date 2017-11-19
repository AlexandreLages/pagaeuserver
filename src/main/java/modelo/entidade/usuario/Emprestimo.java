package modelo.entidade.usuario;

import java.io.Serializable;

public class Emprestimo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4451323866004855440L;
	
	private double valor;
	private long idContato;
	private long idUsuario;
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public long getIdContato() {
		return idContato;
	}
	
	public void setIdContato(long idContato) {
		this.idContato = idContato;
	}
	
	public long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
}