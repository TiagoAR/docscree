package br.ueg.unucet.quid.extensao.interfaces;

/**
 * Interface que identifica uma classe identificavel dentro do framework.
 * Uma classe para ser identificavel ela deve possuir nome e descricao.
 * @author QUID
 *
 */
public interface IIdentificavel {
	/** Retorna o nome de identificacao do componente.
	 * @return Nome do componente.
	 */
	String getNome();
	
	/**Retorna a descricao do componente
	 * @return Descricao do componente
	 */
	String getDescricao();
}
