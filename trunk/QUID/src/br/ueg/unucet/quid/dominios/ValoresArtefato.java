package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * POJO que representa os Valores do Artefato a serem persistidos pelo Serviço de Persistência
 * 
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="valores_artefato")
public class ValoresArtefato extends Persistivel {
	
	/**
	 * ID do Membro no Artefato
	 */
	private Long membro;
	/**
	 * Valor associado ao membro
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((membro == null) ? 0 : membro.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ValoresArtefato))
			return false;
		ValoresArtefato other = (ValoresArtefato) obj;
		if (membro == null) {
			if (other.membro != null)
				return false;
		} else if (!membro.equals(other.membro))
			return false;
		return true;
	}

}
