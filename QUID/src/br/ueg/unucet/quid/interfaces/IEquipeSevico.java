package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Retorno;

/**
 * Inteface que define as operacoes da camada de servico sobre a entidade Equipe.
 * @author QUID
 *
 * @param <T> Classe que representa a entidade servico.
 */
public interface IEquipeSevico<T> extends IServico<T> {
	/**
	 * Metodo responsavel por realizar a insercao de uma nova equipe no framework.
	 * @param equipe Equipe que sera inserida.
	 * @return Retorno da execucao da operacao.
	 */
	Retorno<String, Collection<String>> inserir(T equipe);
	/**
	 * Metodo responsavel por realizar a alteracao da equipe dentro do framework.
	 * @param equipe Equipe que sera alterada.
	 * @return Retorno da execucao da operacao.
	 */
	Retorno<String, Collection<String>> alterar(T equipe);
	
	/**Metodo responsavel por realizar a pesquisa de uma equipe dentro do framework.
	 * @param equipe Equipe que sera pesquisada.
	 * @return Retorno da execucao da operacao.
	 */
	Retorno<String, Collection<Equipe>> pequisarEquipes(T equipe);
}
