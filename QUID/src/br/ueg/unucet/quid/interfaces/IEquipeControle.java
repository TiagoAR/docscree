package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Equipe;


/**
 * Inteface que define as operacoes do controlador da entidade equipe.
 * @author QUID
 *
 * @param <T> Classe que representa a entidade equipe.
 * @param <oid> Classe da chave primaria da equipe.
 */
public interface IEquipeControle<T, oid> extends IControle<T, oid> {
	/**
	 * Metodo que realiza a pesquisa da equipe, dado como restricao os atributos preenchidos da entidade.
	 * @param equipe Equipe que sera pesquisada
	 * @return Lista de equipe encontradas no banco que tenham as restricoes
	 */
	Collection<Equipe> pesquisarEquipe(T equipe);
}
