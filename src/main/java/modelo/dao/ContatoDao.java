package modelo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.entidade.usuario.Contato;

@Stateless
public class ContatoDao extends Repository<Contato>{

	public List<Contato> todosAtivos() {
        String jpql = "select c from Contato c where c.ativo = :ativo";
        TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
        query.setParameter("ativo", true);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	public List<Contato> listarPorUsuario(long id) {
        String jpql = "select c from Contato c where c.ativo = :ativo and c.usuario.id = :id";
        TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
        query.setParameter("ativo", true);
        query.setParameter("id", id);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	public boolean inserirContato(Contato contato){
		if(pesquisarContato(contato.getUsername(), contato.getUsuario().getId()) == null){
			em.persist(contato);
			return true;
		}
		return false;
	}
	
	public void atualizarContato(Contato contato){
		em.persist(contato);
	}
	
	public void deletarContato(long id){
		desativarComId(id);
	}
	
	public Contato pesquisarContato(String username, long idUsuario){
		String jpql = "select c from Contato c where c.username = :username and c.usuario.id = :idUsuario";
		TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("username", username);
		
		try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	public Contato pesquisarContatoId(long id){
		String jpql = "select c from Contato c where c.id = :id";
		TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
		query.setParameter("id", id);
		try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	protected void desativarOuAtivar(Long id, boolean ativar) {
	    String jpql = "update Contato c set c.ativo = :ativo where c.id = :id";
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