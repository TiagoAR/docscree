package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.Servico;
import br.ueg.unucet.quid.interfaces.IDAOServico;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade Servico.
 * @author QUID
 *
 */
@Repository("DAOServico")
public class DAOServico extends DAOGenerica<Servico, Long> implements IDAOServico<Servico, Long>{

}
