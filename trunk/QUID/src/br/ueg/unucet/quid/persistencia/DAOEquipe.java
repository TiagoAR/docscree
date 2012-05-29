package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.interfaces.IDAOEquipe;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade equipe.
 * @author QUID
 *
 */
@Repository
public class DAOEquipe extends DAOGenerica<Equipe, Long> implements IDAOEquipe<Equipe, Long>{

}
