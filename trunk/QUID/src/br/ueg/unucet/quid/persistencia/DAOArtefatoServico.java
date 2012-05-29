package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.ArtefatoServico;
import br.ueg.unucet.quid.interfaces.IDAOArtefatoServico;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade ArtefatoServico.
 * @author QUID
 *
 */
@Repository
public class DAOArtefatoServico extends DAOGenerica<ArtefatoServico, Long> implements IDAOArtefatoServico<ArtefatoServico, Long> {

}
