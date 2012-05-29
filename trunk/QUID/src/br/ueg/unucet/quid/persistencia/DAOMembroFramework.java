package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.MembroFramework;
import br.ueg.unucet.quid.interfaces.IDAOMembroFramework;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade MembroFramework.
 * @author QUID
 *
 */
@Repository("DAOMembroFramework")
public class DAOMembroFramework extends DAOGenerica<MembroFramework, Long> implements IDAOMembroFramework<MembroFramework, Long>{

}
