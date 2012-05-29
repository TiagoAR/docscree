package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.TipoMembro;
import br.ueg.unucet.quid.interfaces.IDAOTipoMembro;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade TipoMembro.
 * @author QUID
 *
 */
@Repository("DAOTipoMembro")
public class DAOTipoMembro extends DAOGenerica<TipoMembro, Long> implements IDAOTipoMembro<TipoMembro, Long>{

}
