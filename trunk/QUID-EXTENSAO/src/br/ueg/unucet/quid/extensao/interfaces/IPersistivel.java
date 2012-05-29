package br.ueg.unucet.quid.extensao.interfaces;

import java.io.Serializable;

/**
 * Inteface que identifica se uma classe e persitivel pelo framework.
 * Toda classe para ser persistida deve ter o codigo.
 * @author QUID
 *
 * @param <T>Classe que representa o codigo.
 */
public interface IPersistivel<T> extends Serializable {
	/**
	 * Metodo que pega o valor do codigo.
	 * @return Valor do codigo.
	 */
	T getCodigo();
	/**
	 * Atribui um valor ao codigo.
	 * @param codigo Valor a ser atribuido ao codigo.
	 */
	void setCodigo(T codigo);
}
