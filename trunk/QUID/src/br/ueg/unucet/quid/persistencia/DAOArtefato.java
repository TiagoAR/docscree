package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.interfaces.IDAOArtefato;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade Artefato.
 * @author QUID
 *
 */
@Repository("DAOArtefato")
public class DAOArtefato extends DAOGenerica<Artefato, Long> implements IDAOArtefato<Artefato, Long>{

}
