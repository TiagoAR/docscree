package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;

@SuppressWarnings("serial")
@Entity
@Table(name="valores_artefato")
public class ValoresArtefato extends Persistivel {
	
	private Long membro;
	private byte[] valor;

	/**
	 * @return the membro
	 */
	public Long getMembro() {
		return membro;
	}

	/**
	 * @param membro
	 *            the membro to set
	 */
	public void setMembro(Long membro) {
		this.membro = membro;
	}

	/**
	 * @return the valor
	 */
	public byte[] getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(byte[] valor) {
		this.valor = valor;
	}

}
