package br.ueg.unucet.quid.persistencia;

import org.springframework.stereotype.Repository;

import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.interfaces.IDAOUsuario;

/**
 * Classe que define as operacoes de persistencia que ocorrem sobre a entidade Usuario.
 * @author QUID
 *
 */
@Repository
public class DAOUsuario extends DAOGenerica<Usuario, Long> implements IDAOUsuario<Usuario, Long>{

}
