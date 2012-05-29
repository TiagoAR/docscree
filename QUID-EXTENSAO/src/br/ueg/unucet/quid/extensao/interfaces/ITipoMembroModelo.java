package br.ueg.unucet.quid.extensao.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;

/**
 * Inteface que representa um TipoMembroModelo no framework.
 * @author QUID
 *
 */
public interface ITipoMembroModelo extends ITipoMembro, IIdentificavel {

	/**
	 * Retorna um Inteiro do java.sql.Types que indica o tipo de dado para
	 * persistencia
	 * 
	 * @return Inteiro do java.sql.Types
	 */
	Integer getTipoDadoPersistencia();

	/**
	 * Retorna se o TipoMembro e estatico ou nao.
	 * 
	 * @return True se o TipoMembro for estatico, False caso contrario.
	 */
	boolean isEstatico();

	/**
	 * Retorna o layout da mascara de prenchimento. Esse layout segue o seguinte
	 * padrao: Z para letras, 0 para numeros e # para valores alfanumericos.
	 * 
	 * @return String referente a mascara.
	 */
	String getMascara();

	/**
	 * Retorna o valor possivel de entrada de dado do TipoMembro
	 * @return DominioTipoMembro  correspondente ao dominio
	 */
	DominioEntradaEnum getDominio();

	/** Realiza a validacao de um dominio especifico EX(CPF, CNPJ, CEP, etc).
	 * @param valor Valor informado pelo usuario
	 * @return True se o valor esta correto, false caso contrario
	 */
	boolean isValorValido(Object valor);

	/**Retorna se o TipoMembro e indexavel
	 * @return True se o TipoMembro e indexavel, False caso contrario
	 */
	boolean isIndexavel();

	/**Realiza a validacao dos parametros do TipoMembro
	 * @return True se os parametros sao validos, False caso contrario
	 */
	boolean isParametrosValidos();
	
	/**Retorna O valor do TipoMembro a ser persistido
	 * @return O valor a ser persistido
	 */
	
	Object getValor();
	
	/**Seta o valor do tipo a ser persistido
	 * @param valor do TipoMembro
	 */
	void setValor(Object valor);
	
	
	/**Retorna lista de nomes dos parametros invalidos
	 * @return Lista de nomes dos parametros invalidos
	 */
	Collection<String> getNomesParametrosInvalidos();

}
