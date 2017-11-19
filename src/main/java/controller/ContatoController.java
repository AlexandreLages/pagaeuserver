package controller;

import java.util.ArrayList;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;
import modelo.dao.ContatoDao;
import modelo.dao.TransacaoDao;
import modelo.dao.UsuarioDao;
import modelo.entidade.usuario.Contato;
import modelo.entidade.usuario.Emprestimo;
import modelo.entidade.usuario.Transacao;
import modelo.entidade.usuario.Usuario;

@Controller
@Path(value = "/contato")
public class ContatoController {

	private Result result;
	private ContatoDao contatos;
	private UsuarioDao usuarios;
	private TransacaoDao transacoes;
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public ContatoController(){
	}
	
	@Inject
	public ContatoController(Result result, ContatoDao contatos, UsuarioDao usuarios, TransacaoDao transacoes){
		this.result = result;
		this.contatos = contatos;
		this.usuarios = usuarios;
		this.transacoes = transacoes;
	}
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void inserir(Contato c){
		Usuario usuario = usuarios.pesquisarUsuarioId(c.getId());
		Usuario novoContato = usuarios.pesquisarUsuarioUsername(c.getUsername());
		if(novoContato != null){
			Contato contato = new Contato();
			contato.setEmail(novoContato.getEmail());
			contato.setNome(novoContato.getNome());
			contato.setTelefone(novoContato.getTelefone());
			contato.setUsername(novoContato.getUsername());
			contato.setUsuario(usuario);
			contatos.inserirContato(contato);
			result.use(Results.status()).created();
		}else{
			result.use(Results.status()).notFound();
		}
	}
	
	@Post
	@Path(value = {"/emprestimo", "/emprestimo/"})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void realizarEmprestimo(Emprestimo e){
		Contato contatoCredor = contatos.pesquisarContatoId(e.getIdContato());
		Usuario usuarioCredor = usuarios.pesquisarUsuarioUsername(contatoCredor.getUsername());
		
		Contato contatoDevedor = contatos.pesquisarContato(contatoCredor.getUsuario().getUsername(), usuarioCredor.getId());
		Usuario usuarioDevedor = usuarios.pesquisarUsuarioId(e.getIdUsuario());
		
		double saldoAnterior = contatoCredor.getSaldo();
		
		Transacao transacaoCredor = new Transacao();
		transacaoCredor.setUsuario(usuarioCredor);
		transacaoCredor.setContato(contatoDevedor);
		transacaoCredor.setTipo(0);
		transacaoCredor.setValor(e.getValor());
		transacaoCredor.setSaldoAnterior(saldoAnterior);
		double saldoAtual = contatoCredor.getSaldo() - e.getValor();
		transacaoCredor.setSaldoAtual(saldoAtual);
		contatoCredor.setSaldo(saldoAtual);
		contatos.atualizar(contatoCredor);
		
		Transacao transacaoDevedor = new Transacao();
		transacaoDevedor.setUsuario(usuarioDevedor);
		transacaoDevedor.setContato(contatoCredor);
		transacaoDevedor.setTipo(1);
		transacaoDevedor.setValor(e.getValor());
		transacaoDevedor.setSaldoAnterior(contatoDevedor.getSaldo());
		saldoAtual = contatoDevedor.getSaldo() + e.getValor();
		transacaoDevedor.setSaldoAtual(saldoAtual);
		contatoDevedor.setSaldo(saldoAtual);
		contatos.atualizar(contatoDevedor);
		
		transacoes.inserir(transacaoCredor);
		transacoes.inserir(transacaoDevedor);
	}
	
	@Get
	@Path(value = {"/lista", "/lista/"})
	public void listar(){
		ArrayList<Contato> contatosList = (ArrayList<Contato>) contatos.todosAtivos();
		result.use(Results.json()).withoutRoot().from(contatosList).serialize();
	}
	
	@Get
	@Path(value = {"/lista/{id}", "/lista/{id}/"})
	public void listar(long id){
		ArrayList<Contato> contatosList = (ArrayList<Contato>) contatos.listarPorUsuario(id);
		result.use(Results.json()).withoutRoot().from(contatosList).serialize();
	}
	
	@Get
	@Path(value = {"/{id}", "/{id}/"})
	public void pesquisar(long id){
		Contato contato = contatos.pesquisarContatoId(id);
		result.use(Results.json()).withoutRoot().from(contato).serialize();
	}
	
	@Delete
    @Path(value = "/{id}")
    public void remover(Long id) {
        contatos.desativarComId(id);
        result.use(Results.status()).ok();
    }
}