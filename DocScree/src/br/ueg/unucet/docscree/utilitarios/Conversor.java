package br.ueg.unucet.docscree.utilitarios;

import java.lang.reflect.InvocationTargetException;

/**
 * Classe de conversores
 * 
 * @author Diego
 * 
 */
public class Conversor {

	/**
	 * Metodo responsável por converter uma String para um Enum. Essa conversao
	 * e feita procurando o enum que tenha o mesmo nome do valor a ser
	 * convertido.
	 * 
	 * @param Class
	 *            <T> classField Classe enum.
	 * @param String
	 *            value Valor a ser convertido.
	 * @return Enum do valor string.
	 */
	public static <T extends Object> T castParaEnum(Class<T> classField,
			String value) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		T[] enums = classField.getEnumConstants();
		int i = 0;
		while (!enums[i].toString().equalsIgnoreCase(value))
			i++;
		return enums[i];
	}

}
