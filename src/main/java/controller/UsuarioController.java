package controller;

import java.util.ArrayList;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;
import modelo.dao.UsuarioDao;
import modelo.entidade.usuario.Usuario;

@Controller
@Path(value = "/usuario")
public class UsuarioController {
	
	private Result result;
	private UsuarioDao usuarios;
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public UsuarioController(){
	}
	
	@Inject
	public UsuarioController(Result result, UsuarioDao usuarios){
		this.result = result;
		this.usuarios = usuarios;
	}
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void inserir(Usuario usuario){
		if(usuarios.inserirUsuario(usuario)){
			result.use(Results.status()).created();
		}else{
			result.use(Results.status()).conflict();
		}
	}
	
	@Get
	@Path(value = {"/lista", "/lista/"})
	public void listar(){
		ArrayList<Usuario> usuariosList = (ArrayList<Usuario>) usuarios.todosAtivos();
		result.use(Results.json()).withoutRoot().from(usuariosList).serialize();
	}
	
	@Get
	@Path(value = {"/login/{email}/{senha}", "/login/{email}/{senha}/"})
	public void logar(String email, String senha){
		Usuario usuario = usuarios.pesquisarUsuarioLogin(email, senha); 
		if(usuario != null){
			result.use(Results.json()).withoutRoot().from(usuario).serialize();
		}else{
			result.use(Results.status()).notFound();
		}
	}
}