package modelo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.entidade.usuario.Usuario;

@Stateless
public class UsuarioDao extends Repository<Usuario>{

	public List<Usuario> todosAtivos() {
        String jpql = "select c from Usuario c where c.ativo = :ativo";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        query.setParameter("ativo", true);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	public boolean inserirUsuario(Usuario usuario){
		if(pesquisarUsuario(usuario.getEmail(), usuario.getUsername()) == null){
			em.persist(usuario);
			return true;
		}
		return false;
	}
	
	public Usuario pesquisarUsuario(String email, String username){
		String jpql = "select c from Usuario c where c.email = :email or c.username = :username";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		query.setParameter("email", email);
		query.setParameter("username", username);
		try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	public Usuario pesquisarUsuarioId(long id){
		String jpql = "select c from Usuario c where c.id = :id";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		query.setParameter("id", id);
		try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	public Usuario pesquisarUsuarioUsername(String username){
		String jpql = "select c from Usuario c where c.username = :username";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		query.setParameter("username", username);
		try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	public Usuario pesquisarUsuarioLogin(String email, String senha){
		String jpql = "select c from Usuario c where (c.email = :email or c.username = :email) and c.senha = :senha";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	protected void desativarOuAtivar(Long id, boolean ativar) {
	    String jpql = "update Usuario c set c.ativo = :ativo where c.id = :id";
	    Query query = em.createQuery(jpql);
	    query.setParameter("ativo", ativar);
	    query.setParameter("id", id);
	    query.executeUpdate();
	}
	
	public void desativarComId(Long id) {
	    desativarOuAtivar(id, false);
	}
	 
	public void ativarComId(Long id) {
	    desativarOuAtivar(id, true);
	}
}