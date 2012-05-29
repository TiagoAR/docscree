package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Retorno;

/**
 * Classe que representa as operacoes de servico sobre o usuario/
 * @author QUID
 *
 * @param <T>Entidade que representa o usuario dentro do framework.
 */
public interface IUsuarioServico<T> extends IServico<T>{
	
	/**
	 * Metodo que realiza a insercao de um novo usuario no framework.
	 * @param usuario Usuario que sera inserido.
	 * @return Retorno da execucao, com sucesso ou falha. Caso seje falha, retornara os nomes dos campos invalidos no usuario.
	 */
	Retorno<String, Collection<String>> inserir(T usuario);
	/**
	 * Metodo que realiza a pesquisa de usuarios dentro do framework. 
	 * @param usuario Usuario que sera pesquisado dentro do framework com base nos campos preenchidos do objeto.
	 * @return Retorno da execucao da operacao, com a lista de usuarios preenchidos.
	 */
	Retorno<String, Collection<T>> pesquisar(T usuario);
	/**
	 * Metodo responsavel por realizar a alteracao de um usuario dentro de um framework.
	 * @param usuario Usuario que sera alterado.
	 * @return Retorno da alteracao do usuario.
	 */
	Retorno<String, Collection<String>> alterar(T usuario);
	

}
