package br.ueg.unucet.docscree.controladores;

import java.util.Collection;

import br.ueg.unucet.docscree.interfaces.ICRUDControle;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Retorno;

/**
 * Controlador específico para o caso de uso Manter Modelo
 * 
 * @author Diego
 *
 */
public class ModeloControle extends GenericoControle<Modelo> {

	/**
	 * @see ICRUDControle#acaoSalvar()
	 */
	@Override
	public boolean acaoSalvar() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see ICRUDControle#acaoSalvar()
	 */
	@Override
	public boolean acaoExcluir() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see ICRUDControle#setarEntidadeVisao(SuperCompositor)
	 */
	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see GenericoControle#executarListagem()
	 */
	@Override
	protected Retorno<String, Collection<Modelo>> executarListagem() {
		// TODO Auto-generated method stub
		return null;
	}

}
