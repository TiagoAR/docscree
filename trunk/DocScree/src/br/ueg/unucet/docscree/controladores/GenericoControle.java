package br.ueg.unucet.docscree.controladores;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;

@SuppressWarnings("unchecked")
public class GenericoControle<E extends Persistivel> extends SuperControle {

	/**
	 * Método que retorna a entidade do mapeador de atributos.
	 * 
	 * @return E entidade
	 */
	protected E getEntidade() {
		return (E) super.getMapaAtributos().get("entidade");
	}
}
