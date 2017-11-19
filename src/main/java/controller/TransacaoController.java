package controller;

import java.util.ArrayList;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import modelo.dao.TransacaoDao;
import modelo.entidade.usuario.Transacao;

@Controller
@Path(value = "/transacao")
public class TransacaoController {
	
	private Result result;
	private TransacaoDao transacoes;
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public TransacaoController(){
	}
	
	@Inject
	public TransacaoController(Result result, TransacaoDao transacoes){
		this.result = result;
		this.transacoes = transacoes;
	}
	
	@Get
	@Path(value = {"/lista/{idUsuario}/{idContato}", "/lista/{idUsuario}/{idContato}/"})
	public void listar(long idUsuario, long idContato){
		ArrayList<Transacao> transacoesList = (ArrayList<Transacao>) transacoes.transacoesUsuario(idUsuario, idContato);
		result.use(Results.json()).withoutRoot().from(transacoesList).serialize();
	}
}
