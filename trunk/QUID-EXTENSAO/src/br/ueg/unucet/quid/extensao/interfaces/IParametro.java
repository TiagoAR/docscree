package br.ueg.unucet.quid.extensao.interfaces;

import java.io.Serializable;
import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;

/**
 * Classe que identifica um parametro para o TipoMembro dentro do framework.
 * @author QUID
 *
 * @param <T>
 */
public interface IParametro<T> extends Serializable {

	/**
	 * Retorna o nome do parametro.
	 * 
	 * @return
	 */
	String getNome();

	/**
	 * Retorna o texto de apresentacao do parametro para o usuário
	 * 
	 * @return String
	 */
	String getRotulo();

	/**
	 * Obtem o valor do parametro
	 * 
	 * @return Valor do parametro
	 */
	T getValor();

	/**
	 * Obtem o valor do parametro em string para apresentacao na visao.
	 * 
	 * @return
	 */
	String getValorString();

	/**
	 * Atribui um valor ao parametro
	 * 
	 * @param valor
	 *            Valor para ser atribuido
	 */
	void setValor(String valor);
	
	void setValorClass(T valor);

	/**
	 * Retorna se determinado parametro e obrigatorio
	 * 
	 * @return True se o parametro for obrigatorio, false caso contrario
	 */
	boolean isObrigatorio();

	/**
	 * Retorna uma lista de valores possiveis para esse parametro, caso
	 * necessaorio
	 * 
	 * @return Lista de Dominios
	 */
	Collection<String> getListaDominioTipo();

	/**
	 * Retorna o dominio de entrada do parametro caso nao seje definido uma
	 * lista de dominios no metodo getListaDominioTipo
	 * 
	 * @return Dominio de entrada do parametro
	 */
	DominioEntradaEnum getDominioEntrada();

}
