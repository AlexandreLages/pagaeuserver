package modelo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public abstract class Repository<T> {

	Class<T> tipo = retornaTipo();
	
	@PersistenceContext(unitName = "pagaeu-ds")
	protected EntityManager em;
	
	public void inserir(T entidade){
		em.persist(entidade);
	}
	
	public void deletar(T entidade){
		em.remove(entidade);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> todos() {
        CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(tipo));
        return (List<T>) em.createQuery(cq).getResultList();
    }
	
	public T comId(long id) {
        return em.find(tipo, id);
    }
 
    public T atualizar(T entidade) {
        return em.merge(entidade);
    }
    
    @SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
        Class<?> clazz = this.getClass();
         
        while (!clazz.getSuperclass().equals(Repository.class)) {
            clazz = clazz.getSuperclass();
        }
         
        ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
        return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }
}
