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
	
}
