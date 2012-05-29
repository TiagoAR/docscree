package br.ueg.unucet.quid.utilitarias;

import java.util.Date;

/**
 * Classe responsavel por verificar se um deternimado valor e vazio.
 * @author QUID
 *
 */
public class EmptyValue {
	
	/**
	 * Metodo que verifica se determinado valor e vazio.
	 * @param value Valor que sera verificado.
	 * @returnTrue caso o valor seje vazio, false caso contrario.
	 */
	public static boolean isEmpty(Object value){
		boolean aux = true;
		if(value instanceof String){
			aux = isEmptyString(value.toString());
		}else if(value instanceof Double){
				aux = isEmptyDouble((Double)value);
			}else if(value instanceof Integer){
				aux = isEmptyInteger((Integer)value);
			}else if(value instanceof Date){
				aux = isEmptyDate((Date) value);
			}else aux = value == null;
		return aux;
	}
	/**
	 * Metodo que verifica se uma string e vazia.
	 * @param value Valor string que sera verificado.
	 * @returnTrue caso a string seja vazia, false caso contrario.
	 */
	private static boolean isEmptyString(String value){
		return value.equalsIgnoreCase("");
	}
	/**
	 * Metodo que verifica se um doble e vazio.
	 * @param value Valor dobble que sera verificado.
	 * @returnTrue caso o double seja vazio, false caso contrario.
	 */
	private static boolean isEmptyDouble(Double value){
		return value == 0.0;
	}
	/**
	 * Metodo que verifica se um integer e vazio.
	 * @param value Valor integer que sera verificado.
	 * @returnTrue caso o integer seja vazio, false caso contrario.
	 */
	private static boolean isEmptyInteger(Integer value){
		return value == 0;
	}
	/**
	 * Metodo que verifica se uma date e vazia.
	 * @param value Valor date que sera verificado.
	 * @returnTrue caso a date seja vazia, false caso contrario.
	 */
	private static boolean isEmptyDate(Date date){
		return date == null;
	}
	
}
