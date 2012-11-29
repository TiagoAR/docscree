package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.interfaces.IDAOProjeto;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade Projeto.
 * @author QUID
 *
 */
@Repository
public class DAOProjeto extends DAOGenerica<Projeto, Long> implements IDAOProjeto<Projeto, Long>{

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.persistencia.DAOGenerica#inserir(java.lang.Object)
	 */
	@Override
	public void inserir(Projeto entidade) {
		getEntityManager().merge(entidade);
		getEntityManager().flush();
	}
	
}
