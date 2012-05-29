package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.excessoes.QuidExcessao;

/**
 * Interface que representa as operacoes basicas de toda classe controle dentro do framework.
 * @author QUID
 *
 */
public interface IControle <T, oid> {
	/**Metodo responsavel por realizar a acao de salvar uma entidade
	 * @param entidade Entidade que sera persistida
	 * @throws QuidExcessao 
	 */
	void inserir(T entidade) throws QuidExcessao;
	
	/**Metodo responsavel por realizar a acao de alterar uma entidade
	 * @param entidade Entidade que sera alterada
	 * @throws QuidExcessao 
	 */
	void alterar(T entidade) throws QuidExcessao;
	
	/**Metodo responsavel por realizar a acao de deletar uma entidade
	 * @param classe Classe que sera deletada
	 * @param id Valor da chave primaria que sera deletada
	 */
	void remover(Class<T> classe, oid id) throws QuidExcessao;
	
	/**Metodo que retorna uma instancia da classe que foi passado o id
	 * @param classe Classe que sera pesquisada
	 * @param id Valor da chave primaria a ser pesquisada
	 * @return Instancia da classe pesquisada ou null caso ela nao exista
	 */
	T getPorId(Class<T> classe, oid id);
	
	/**Retorna todos os registros cadastrados dessa entidade
	 * @return Lista de registros cadastrados da classe 
	 */
	Collection<T> listar();
	
	/**Metodo que pesquisa a entidade cadastrada no banco de dados. Essa pesquisa
	 * e realizada pelos atributos informados no objeto entidade. Cada atributo informado
	 * sera uma clausula where na construcao da HQL.
	 * @param entidade Entidade que sera pesquisada.
	 * @param colunas Colunas do objeto que se deseja voltar da consulta
	 * @return Lista de objetos que contem as caracteristicas informados no objeto entidade
	 */
	Collection<T> pesquisarPorRestricao(T entidade, String[] colunas);
	
	/**Metodo que pesquisa a entidade cadastrada no banco de dados. Essa pesquisa
	 * e realizada pelos atributos informados no objeto entidade. Cada atributo informado
	 * sera uma clausula where na construcao da HQL
	 * @param entidade Entidade que sera pesquisada.
	 * @param colunas Colunas do objeto que se deseja voltar da consulta
	 * @param ordenamento Ordenamento da consulta(ORDER BY).
	 * @return Lista de objetos que contem as caracteristicas informados no objeto entidade
	 */
	Collection<T> pesquisarPorRestricao(T entidade, String[] colunas, String[] ordenamento);
	
	/**Metodo que retorna se dada operacao realizada dentro do controlador foi de sucesso ou falha
	 * @return True se a operacao foi com sucesso, false caso contrario
	 */
	boolean isSucesso();
}
