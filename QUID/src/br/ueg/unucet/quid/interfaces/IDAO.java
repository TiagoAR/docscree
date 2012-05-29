package br.ueg.unucet.quid.interfaces;

import java.util.List;

/**
 * Interface que define as operacoes basicas sobre as classes DAO.
 * @author QUID
 *
 * @param <T> Classe para qual o DAO ira operar.
 * @param <oid> Clase da classe primaria da entidade
 */
public interface IDAO<T, oid> {
	
	/**
	 * Metodo responsavel por inserir uma nova entidade no banco.
	 * @param entidade A entidade que sera inserida.
	 */
	void inserir(T entidade);
	/**
	 * Metodo responsavel por alterar uma entidade no banco de dados.
	 * @param entidade Entidade que sera alterada.
	 */
	void alterar(T entidade);
	
	/**
	 * Metodo que remove uma entidade do banco de dados.
	 * @param classe Classe que pertence a entidade
	 * @param id Chave primaria da entidade.
	 */
	void remover(Class<T> classe, oid id);
	
	/**
	 * Metodo responsavel por pesquisar uma entidade pela sua chave primaria.
	 * @param classe Clase que sera pesquisada.
	 * @param id Chave primaria da entidade.
	 * @return Entidade.
	 */
	T getPorId(Class<T> classe, oid id);
	/**
	 * Metodo responsavel pos listar todas as entidades cadastradas no banco de dados.
	 * @return Lista de todas as entidades cadastradas no banco de dados.
	 */
	List<T> listar();
	/**
	 * Metodo responsavel por pesquisar uma entidade cadastrada no banco de dados, utilizando como condicoes os atributos preenchidos da entridade.
	 * @param entidade Entidade para qual sera realizada a pesquisa
	 * @param colunas Atributos da entidade que se deseja trazer do banco de dados.
	 * @return Lista de Entidades encontradas com as restricoes.
	 */
	List<T> pesquisarPorRestricao(T entidade, String ... colunas);
	/**
	 * Metodo responsavel por pesquisar uma entidade cadastrada no banco de dados, utilizando como condicoes os atributos preenchidos da entridade.
	 * @param entidade Entidade para qual sera realizada a pesquisa
	 * @param colunas Atributos da entidade que se deseja trazer do banco de dados.
	 * @param ordenamento Atriutos para qual a pesquisa sera ordenada
	 * @return Lista de Entidades encontradas com as restricoes.
	 */
	List<T> pesquisarPorRestricao(T entidade, String[] colunas, String[] ordenamento);
	/**
	 * Metodo que realiza a execucao de uma HQL no padrao definido pelo JPA e traz o retorno em uma lista de vetores de objetos.
	 * @param query String com a HQL que sera executada
	 * @return Lista de vetores de objetos provinda da execucao.
	 */
	List<Object[]> executeQuery(String query);
}
