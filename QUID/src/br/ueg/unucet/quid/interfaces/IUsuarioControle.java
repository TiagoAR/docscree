package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Usuario;

/**
 * Classe que representa as operacoes de controle sobre a entidade Usuario
 * @author QUID
 *
 * @param <T>Entidade que representa o Usuario dentro do framework.
 * @param <oid>Classe da chave primaria da entidade.
 */
public interface IUsuarioControle<T, oid> extends IControle<T, oid>{
	
	Collection<Usuario> pesquisarUsuario(Usuario usuario);
	
}
