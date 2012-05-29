package br.ueg.unucet.quid.extensao.interfaces;

import java.util.Collection;

/**
 * Interface que representa um TipoMembro no framework.
 * @author QUID
 *
 */
public interface ITipoMembro extends IVersionavel {


	/**
	 * Atribui a lista de parametros do TipoMembro modelo
	 * 
	 * @param parametros
	 *            Lista de parametros
	 */
	void setListaParametros(Collection<IParametro<?>> parametros);

	/**
	 * Obtem a lista de parametros do TipoMembro modelo
	 * 
	 * @return Lista de parametros do TipoMembro modelo
	 */
	Collection<IParametro<?>> getListaParametros();
	

}
