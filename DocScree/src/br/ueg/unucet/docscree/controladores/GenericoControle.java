package br.ueg.unucet.docscree.controladores;

import java.util.List;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * Controle Genérico, extende SuperControle e contém operações básicas de todos os controladores persistíveis
 * 
 * @author Diego
 *
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class GenericoControle<E extends Persistivel> extends SuperControle {


	
	/**
	 * Contém a lista de entidade, setado quando chamado a ação listar.
	 */
	protected List<?> lista;
	
	/**
	 * Método que retorna a entidade do mapeador de atributos.
	 * 
	 * @return E entidade
	 */
	protected E getEntidade() {
		return (E) super.getMapaAtributos().get("entidade");
	}

	/**
	 * @return o(a) lista
	 */
	public List<?> getLista() {
		return lista;
	}

	/**
	 * @param lista o(a) lista a ser setado(a)
	 */
	public void setLista(List<?> lista) {
		this.lista = lista;
	}
}
