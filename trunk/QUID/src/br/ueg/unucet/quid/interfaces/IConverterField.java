package br.ueg.unucet.quid.interfaces;

import java.io.Serializable;

/**
 * Interface que define os metodos de conversao de strings para classes.
 * @author QUID
 *
 */
public interface IConverterField extends Serializable{
	
	/**
	 * Metodo responsavel por converter um string em uma instancia de uma classe que seje  um TipoBasico como (Long, Date, Integer, etc)
	 * @param <T> Classe para qual sera convertida a String.
	 * @param classField Classe de T que sera convertido a String
	 * @param value Valor String que sera convertido
	 * @return Instancia da classe T do valor.
	 * @throws Exception Excessao no momento da conversao.
	 */
	public <T extends Object>T convertField(Class<T> classField, String value) throws Exception;
	
}
