package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.ArtefatoMembro;
import br.ueg.unucet.quid.interfaces.IDAOArtefatoMembro;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade ArtefatoMembro.
 * @author QUID
 *
 */
@Repository
public class DAOArtefatoMembro extends DAOGenerica<ArtefatoMembro, Long> implements IDAOArtefatoMembro<ArtefatoMembro, Long>{

}
