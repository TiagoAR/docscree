package br.ueg.unucet.docscree.utilitarios;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.SuperControle;
import br.ueg.unucet.docscree.visao.compositor.GenericoCompositor;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;

/**
 * Classe que executa as reflexões contidas na aplicação.
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Reflexao {

	/**
	 * Método que mapea os atributos vindo da visão e o retorna.
	 * 
	 * @param pVisao
	 *            visão que chamou a ação
	 * @return mapeador atributos mapeados
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static HashMap<String, Object> gerarMapeadorAtributos(
			SuperCompositor<SuperControle> pVisao)
			throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {
		
		
		if (pVisao instanceof GenericoCompositor<?>) {
			Object entidade = ((GenericoCompositor) pVisao).novaEntidade();
			return gerarMapeador(true, entidade, pVisao);
		} else {
			return gerarMapeador(false, null, pVisao);
		}
		
		
	}
	
	/**
	 * Método que gera o HashMap dos atributos, verificando se tem geração de entidades persistíveis ou não.
	 * 
	 * @param temEntidade
	 * @param entidade
	 * @param pVisao
	 * @return mapeador mapa dos atributos
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static HashMap<String, Object> gerarMapeador(boolean temEntidade, Object entidade, SuperCompositor<SuperControle> pVisao) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		HashMap<String, Object> mapeador = new HashMap<String, Object>();
		for (Class classeRefletida = pVisao.getClass(); classeRefletida != null; classeRefletida = classeRefletida
				.getSuperclass()) {
			for (Field campo : classeRefletida.getDeclaredFields()) {
				if (campo.isAnnotationPresent(AtributoVisao.class)) {
					AtributoVisao anotacao = campo
							.getAnnotation(AtributoVisao.class);
					if (temEntidade && anotacao.isCampoEntidade()) {
						setValorObjeto(anotacao.nome(), campo.getType(),
								getValorObjeto(pVisao, campo.getName()),
								entidade);
					} else {
						mapeador.put(anotacao.nome(),
								getValorObjeto(pVisao, campo.getName()));
					}
				}
			}
		}
		mapeador.put("entidade", entidade);
		return mapeador;
	}

	/**
	 * Método que seta o valor de um campo a um objeto.
	 * 
	 * @param nomeCampo
	 *            nome do campo
	 * @param classeCampo
	 *            classe que representa o campo
	 * @param valor
	 *            valor do campo
	 * @param objeto
	 *            a ser setado o valor
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void setValorObjeto(String nomeCampo, Class classeCampo,
			Object valor, Object objeto) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Class objetoRefletido = objeto.getClass();
		Method metodo = objetoRefletido.getMethod(
				"set" + nomeCampo.substring(0, 1).toUpperCase()
						+ nomeCampo.substring(1), classeCampo);
		metodo.invoke(objeto, valor);
	}

	/**
	 * Método que traz o valor do campo
	 * 
	 * @param objeto
	 * @param nomeCampo
	 *            nome do campo
	 * @return valor do campo
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object getValorObjeto(Object objeto, String nomeCampo)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Class entityClass = objeto.getClass();
		Method metodo = entityClass.getMethod("get"
				+ nomeCampo.substring(0, 1).toUpperCase()
				+ nomeCampo.substring(1));
		return metodo.invoke(objeto);
	}

}
