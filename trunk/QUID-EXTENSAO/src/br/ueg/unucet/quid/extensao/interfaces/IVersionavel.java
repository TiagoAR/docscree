package br.ueg.unucet.quid.extensao.interfaces;


/**
 * Interface que identifica uma classe Versionavel dentro do framework.
 * @author QUID
 *
 */
public interface IVersionavel extends IIdentificavel {

	/**
	 * Retorna o numero que identifica a versao do objeto versionavel.
	 * 
	 * @return Numero da versao
	 */
	Integer getVersao();

	/**
	 * Retorna o numero que identifica a revisao do objeto versionavel.
	 * 
	 * @return Numero da resivao
	 */
	Integer getRevisao();
}
