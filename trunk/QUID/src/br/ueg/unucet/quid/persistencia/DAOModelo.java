package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.interfaces.IDAOModelo;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade Modelo.
 * @author QUID
 *
 */
@Repository
public class DAOModelo extends DAOGenerica<Modelo, Long> implements IDAOModelo<Modelo, Long> {

}
