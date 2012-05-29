package br.ueg.unucet.docscree.utilitarios;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import br.ueg.unucet.docscree.annotation.AtributoVisao;
import br.ueg.unucet.docscree.controladores.SuperControle;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Reflexao {

	public static HashMap<String, Object> gerarMapeadorAtributos(
			SuperCompositor<SuperControle> pVisao)
			throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {
		HashMap<String, Object> mapeador = new HashMap<String, Object>();
		Object entidade = pVisao.novaEntidade();
		for (Class classeRefletida = pVisao.getClass(); classeRefletida != null; classeRefletida = classeRefletida
				.getSuperclass()) {
			for (Field campo : classeRefletida.getDeclaredFields()) {
				if (campo.isAnnotationPresent(AtributoVisao.class)) {
					AtributoVisao anotacao = campo
							.getAnnotation(AtributoVisao.class);
					if (anotacao.isCampoEntidade()) {
						setValorObjeto(anotacao.nome(),
								getValorObjeto(pVisao, campo.getName()),
								entidade);
					} else {
						mapeador.put(anotacao.nome(), getValorObjeto(pVisao, campo.getName()));
					}
				}
			}
		}
		mapeador.put("entidade", entidade);
		return mapeador;
	}

	public static void setValorObjeto(String nomeCampo, Object valor, Object objeto)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Class objetoRefletido = objeto.getClass();
		Method metodo = objetoRefletido.getMethod(
				"set" + nomeCampo.substring(0, 1).toUpperCase()
						+ nomeCampo.substring(1), valor.getClass());
		metodo.invoke(objeto, valor);
	}

	public static Object getValorObjeto(Object objeto, String nomeCampo)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Class entityClass = objeto.getClass();
		Method method = entityClass.getMethod("get"
				+ nomeCampo.substring(0, 1).toUpperCase()
				+ nomeCampo.substring(1));
		return method.invoke(objeto);
	}

}
