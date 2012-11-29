package br.ueg.unucet.quid.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.interfaces.IDAOArtefatoPreenchido;

@Repository
public class DAOArtefatoPreenchido extends
		DAOGenerica<ArtefatoPreenchido, Long> implements IDAOArtefatoPreenchido<ArtefatoPreenchido, Long> {
	
	/**
	 * Atributo injetado pelo spring como provedor JPA. 
	 */
	@PersistenceContext(unitName="persistenceUnit2")
	protected EntityManager entityManager;

	/**
	 * @return the entityManager
	 */
	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
