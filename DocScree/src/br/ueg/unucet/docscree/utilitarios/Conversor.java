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
	 * Método responsável por converter uma String para um Enum. Essa conversão
	 * é feita procurando o enum que tenha o mesmo nome do valor a ser
	 * convertido.
	 * 
	 * @param classField Classe enum.
	 * @param value Valor a ser convertido.
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
