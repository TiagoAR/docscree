package br.ueg.unucet.quid.extensao.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Interface que define uma classe que possibilita a listagem de componente para um combobox.
 * @author QUID
 *
 * @param <T>Classe que sera listavel.
 */
public interface IListavel<T extends Object> extends Serializable {

	/**
	 * Metodo que retorna a lista de objetos.
	 * @return Lista de objetos.
	 */
	List<T> getComboList();

	/**Metodo que identifica se os objetos dentro de uma lista devem ser repesquisados.
	 * @return
	 */
	boolean isOld();

}
