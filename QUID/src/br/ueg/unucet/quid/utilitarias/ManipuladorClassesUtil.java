package br.ueg.unucet.quid.utilitarias;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe responsavel por realizar a manipulacao de classes.
 * @author QUID
 *
 */
public class ManipuladorClassesUtil {

	/**
	 * Metodo que seleciona um dentre um conjunto de classes, aquelas que assinam uma determinada interface.
	 * @param classes Classes a serem verificada.
	 * @param interfaceAssinada Interface assinada.
	 * @return Lista de classes que assinam a interface.
	 */
	public static Collection<Class<?>> getClassesAssinanteInterface(Collection<Class<?>> classes, Class<?> interfaceAssinada){
		Collection<Class<?>> lista = new ArrayList<Class<?>>();
		for (Class<?> classe : classes) {
			if(interfaceAssinada.isAssignableFrom(classe)){
				lista.add(classe);
			}
		}
		return lista;
	}
	
	/**
	 * Metodo resposavel por instanciar as classes de uma lista.
	 * @param classes Lista de classes a serem intanciadas.
	 * @return Lista de objetos advindos da instanciacao das classes.
	 */
	public static Collection<Object> getInstancias(Collection<Class<?>> classes){
		Collection<Object> instancias = new ArrayList<Object>();
		for (Class<?> classe : classes) {
			Object objeto = novaInstancia(classe);
			if(objeto != null)
					instancias.add(objeto);
		}
		return instancias;
	}
	
	/**
	 * Metodo que realiza a instancia de uma classe especifica.
	 * @param classe Classe a ser instanciada.
	 * @return Objeto advindo da intancia da classe.
	 */
	private static Object novaInstancia(Class<?> classe){
		Object objeto = null;
		try {
			objeto = classe.newInstance();
		} catch (Exception e) {} 
		return objeto;
	}
	
	/**
	 * Metodo que converte uma lista de objetos para uma lista de objetos tipados em uma determinada classe.
	 * @param <T> Classe que sera tipado os objetos da lista.
	 * @param classeConverter Classe para qual os objetos serao tipados.
	 * @param objetos Lista de objetos que serao tipados.
	 * @return Lista de objetos tipados sobre a classeConverter.
	 */
	public static <T> Collection<T> converteLista(Class<T> classeConverter, Collection<Object> objetos){
		Collection<T> listaConvertida = new ArrayList<T>();
		for (Object objeto : objetos) {
			listaConvertida.add((T) objeto); 
		}
		return listaConvertida;
	}
}
