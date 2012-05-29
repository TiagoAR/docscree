package br.ueg.unucet.quid.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ueg.unucet.quid.interfaces.IDAO;

/**
 * Classe que realiza as operacoes de persistencia definidas na interface IDAO.
 * @author QUID
 *
 * @param <T>Classe da entidade que sera realizada as operacoes de persistencia.
 * @param <oid>Classe da chave primaria da entidade.
 */
public class DAOGenerica<T, oid> implements IDAO<T, oid>{
	

	/**
	 * Atributo injetado pelo spring como provedor JPA. 
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#inserir(java.lang.Object)
	 */
	@Override
	public void inserir(T entidade) {
		entityManager.persist(entidade);
		entityManager.flush();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#alterar(java.lang.Object)
	 */
	@Override
	public void alterar(T entidade) {
		entityManager.merge(entidade);
		entityManager.flush();
		
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#remover(java.lang.Class, java.lang.Object)
	 */
	@Override
	public void remover(Class<T> classe, oid id) {
		entityManager.remove(entityManager.getReference(classe, id));
		entityManager.flush();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#getPorId(java.lang.Class, java.lang.Object)
	 */
	@Override
	public T getPorId(Class<T> classe, oid id) {
		return (T) entityManager.find(classe, id);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#listar()
	 */
	@Override
	public List<T> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#pesquisarPorRestricao(java.lang.Object, java.lang.String[])
	 */
	@Override
	public List<T> pesquisarPorRestricao(T entidade, String... colunas) {
		ProcuradorObjeto<T> procurador = new ProcuradorObjeto<T>(entidade);
		List<T> list = null;
		try {
			list = procurador.getByFinder(this, colunas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#pesquisarPorRestricao(java.lang.Object, java.lang.String[], java.lang.String[])
	 */
	@Override
	public List<T> pesquisarPorRestricao(T entidade, String[] colunas, String[] ordenamento) {
		ProcuradorObjeto<T> procurador = new ProcuradorObjeto<T>(entidade);
		List<T> list = null;
		try {
			list = procurador.getByFinder(this, ordenamento, colunas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IDAO#executeQuery(java.lang.String)
	 */
	@Override
	public List<Object[]> executeQuery(String query) {
		return this.entityManager.createQuery(query).getResultList();
	}
	
	//GETTERS AND SETTERS
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
