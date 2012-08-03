package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Identificavel;

/**
 * Classe que representa a abstracao da classe Membro no framework.
 * @author QUID
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="membro")
public class MembroFramework extends Identificavel{
	
	/**
	 * TipoMembro que compotem o membro no framework.
	 */
	@ManyToOne
	private TipoMembro tipoMembro;
	
	/**
	 * Bytes dos parametros do ITipoMembro.
	 */
	private byte[] parametros;
	
	//GETTERS AND SETTERS
	public TipoMembro getTipoMembro() {
		return tipoMembro;
	}
	public void setTipoMembro(TipoMembro tipoMembro) {
		this.tipoMembro = tipoMembro;
	}
	public byte[] getParametros() {
		return parametros;
	}
	public void setParametros(byte[] parametros) {
		this.parametros = parametros;
	}
	
	
	
	
}
