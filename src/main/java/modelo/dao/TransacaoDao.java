package modelo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import modelo.entidade.usuario.Transacao;

@Stateless
public class TransacaoDao extends Repository<Transacao>{
	
	public List<Transacao> transacoesUsuario(long idUsuario, long idContato) {
        String jpql = "select c from Transacao c where c.usuario.id = :idUsuario and c.contato.id = :idContato";
        TypedQuery<Transacao> query = em.createQuery(jpql, Transacao.class);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("idContato", idContato);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}