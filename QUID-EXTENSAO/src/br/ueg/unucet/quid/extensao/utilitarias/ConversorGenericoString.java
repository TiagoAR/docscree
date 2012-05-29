package br.ueg.unucet.quid.extensao.utilitarias;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe responsavel por realizar a conversao de String em uma classe que tenha o construtor string.
 * @author QUID
 *
 */
public class ConversorGenericoString {

	/**
	 * Metodo responsavel por converter um valor string em uma classe.
	 * @param <T> Tipo da classe para qual o valor sera convertido.
	 * @param classField Classe para qual o valor sera convertido.
	 * @param value Valor a ser convertido.
	 * @return Instancia da Classe.
	 */
	public static <T extends Object> T convertField(Class<T> classField, String value) {
		Object object = null;
		try {
			if (classField == String.class) {
				object = castString(value);
			} else if (classField == Date.class) {
				object = castDate(value);
			} else {
				if (classField.isEnum()) {
					object = castEnum(classField, value);
				} else if (classField.equals(Double.class)) {
					object = castDouble(classField, value);
				} else {
					object = cast(classField, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) object;
	}

	/**
	 * Metodo que realiza a conversao de uma String para String, colocando seu valor em maiusculo.
	 * @param value
	 * @return
	 */
	private static Object castString(String value) {
		return value.toUpperCase();
	}

	/**
	 * Metodo que realiza a conversao de uma String para Double.
	 * @param <T> Tipo double
	 * @param classField Classe do tipo Double.
	 * @param value Valor a ser convertido.
	 * @return Intancia da classe double com o valor convertido.
	 */
	public static <T extends Object> T castDouble(Class<T> classField, String value) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		value = value.replace(",", ".");
		return cast(classField, value);
	}

	/**
	 * Metodo responsavel por converter uma String para um Enum. Essa conversao e feita procurando o enum que tenha o mesmo nome do valor a ser convertido.
	 * @param <T>Tipo Enum
	 * @param classField Classe enum.
	 * @param value Valor a ser convertido.
	 * @return Enum do valor string.
	 */
	private static <T extends Object> T castEnum(Class<T> classField, String value) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		T[] enums = classField.getEnumConstants();
		int i = 0;
		while (!enums[i].toString().equalsIgnoreCase(value))
			i++;
		return enums[i];
	}

	/**
	 * Metodo que realiza um cast generico procurando o construtor string da classe.
	 * @param <T>Tipo da classe.
	 * @param classField Classe que sera convertido o valor.
	 * @param value Valor a ser convertido.
	 * @return Instancia da classe convertida.
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private static <T extends Object> T cast(Class<T> classField, String value) throws IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Constructor<T> constructor;
		constructor = classField.getConstructor(String.class);
		return constructor.newInstance(value);
	}

	/**
	 * Metodo que realiza a conversao de uma String no formado dd/MM/yyyy para uma data.
	 * @param data String com a data no foramdo dd/MM/yyyy
	 * @return Instancia da data.
	 * @throws ParseException Erro na conversao da data.
	 */
	private static Date castDate(String data) throws ParseException {
		Date date = null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		date = df.parse(data);
		return date;
	}

	/**
	 * Metodo responsavel por pegar o valor String de um objeto.
	 * @param value Objeto a ser pegado o valor string.
	 * @return String do valor do objeto.
	 */
	private String reconvertField(Object value) {
		String aux = "";
		if (value instanceof Date) {
			aux = recastDate((Date) value);
		} else {
			aux = recast(value);
		}
		return aux;
	}

	/**
	 * Metodo que converte uma data para uma string no formato dd/MM/yyyy.
	 * @param date Data a ser convertida.
	 * @return String da data no formato dd/MM/yyyy
	 */
	private static String recastDate(Date date) {
		DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		return dt.format(date);
	}

	/**
	 * Metodo que chama o metodo toString de um objeto.
	 * @param value Objeto a ser chamdo o toString.
	 * @return String do metodo toString.
	 */
	private static String recast(Object value) {
		return value.toString();
	}

}
