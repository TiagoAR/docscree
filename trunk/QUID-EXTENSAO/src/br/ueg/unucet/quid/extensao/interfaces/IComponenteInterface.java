package br.ueg.unucet.quid.extensao.interfaces;

import java.io.Serializable;

/**
 * 
 * 
 * @author Diego
 *
 */
public interface IComponenteInterface extends Serializable {
	
	public void setValor(Object valor);
	
	public Object getValor();
	
	public Object getValor(Object componente);

}
