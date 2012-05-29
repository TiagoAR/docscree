package br.ueg.unucet.quid.extensao.utilitarias;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que extende a classe SuperListavelGenerico, especificado a listagem de um Enum
 * @author QUID
 *
 */
public class ListagemGenericaEnum extends SuperListavelGenerico<String> {

	/**
	 * Classe enum a ser listada.
	 */
	private Class<?> enumClass;

	public ListagemGenericaEnum(Class<?> enumClass) {
		this.enumClass = enumClass;

	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.utilitarias.SuperListavelGenerico#getList()
	 */
	@Override
	public List<String> getList() {
		List<String> list = new ArrayList<String>();
		for (Object object : enumClass.getEnumConstants()) {
			list.add(object.toString());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.utilitarias.SuperListavelGenerico#isOld()
	 */
	@Override
	public boolean isOld() {
		return false;
	}

}
